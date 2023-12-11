package univtln.hafsaoui.rouge.api;


import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.jpa.ClientDAO;
import univtln.hafsaoui.rouge.entities.ImplClient;
import univtln.hafsaoui.rouge.events.Producer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Path("/clients")
public class Client implements Resource{

    @Inject  ClientDAO dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpHeaders headers, @QueryParam("page") Integer page) {
        log.info("[REST]:request get with page"+page+" and headers:"+
                headers.getHeaderString("page"));
        if (headers.getHeaderString("page") == null) {
            List<Dto> clients = dao.getAllOf(page);
            return responseFrom(clients);
        } else{
            try {
                page = Integer.parseInt(headers.getHeaderString("page"));
                List<Dto> clients = dao.getAllOf(page);
                return responseFrom(clients);
            } catch (NullPointerException | NumberFormatException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid header ").build();
            }
        }
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name) {
        log.info("[REST]:request get for client with name:"+name);
        return Response.status(203).entity(dao.get(name).get().toString()).build();
    }

    /**
     * Endpoint to put a specific client from a json
     *
     * @return a String
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String json) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        ImplClient newClient = ImplClient.fromJson(json);
        if (newClient == null){
            return this.errorEntity();
        }
        Set<ConstraintViolation<ImplClient>> violations = validator.validate(newClient);
        if (!violations.isEmpty()) {
            log.error("Err: Contrain violation"+violations.toString());
            return this.violation();
        }
        Producer producer = Producer.of();
        producer.send_client(newClient,"CLIENT-PUT");
        return this.EntityAdded();
    }

    /**
     * Endpoint to delete a specific client from a name
     *
     * @return a String
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String json) {
        ImplClient newClient = ImplClient.fromJson(json);
        if (newClient == null){
            return Response.status(402).build();
        }
        Producer producer = Producer.of();
        producer.send_client(newClient,"CLIENT-DELETE");
        return Response.status(201).build();
    }

    /**
     * Endpoint to get information on all possible action
     *
     * @return a String
     */
    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response help() {
        List<String> methodes = Arrays.stream(this.getClass().getDeclaredMethods())
                .map(Method::getName)
                .toList();
        return Response.status(200).entity(methodes.toString()).build();
    }

}
