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
import univtln.hafsaoui.rouge.daos.jpa.DeliveryDAO;
import univtln.hafsaoui.rouge.entities.ImplDelivery;
import univtln.hafsaoui.rouge.events.Producer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Path("/delivery")
public class Delivery implements Resource{

    @Inject
    DeliveryDAO dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpHeaders headers, @QueryParam("page") Integer page) {
        log.info("[REST]:request get with page"+page+" and headers:"+
                headers.getHeaderString("page"));
        if (headers.getHeaderString("page") == null) {
            List<Dto> deliverys = dao.getAllOf(page);
            return responseFrom(deliverys);
        } else{
            try {
                page = Integer.parseInt(headers.getHeaderString("page"));
                List<Dto> deliverys = dao.getAllOf(page);
                return responseFrom(deliverys);
            } catch (NullPointerException | NumberFormatException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid header ").build();
            }
        }
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("name") String name) {
        return Response.status(501).build();
    }

    /**
     * Endpoint to put a specific delivery from a json
     *
     * @return a String
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String json) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        ImplDelivery newDelivery = ImplDelivery.fromJson(json);
        if (newDelivery == null){
            return this.errorEntity();
        }
        Set<ConstraintViolation<ImplDelivery>> violations = validator.validate(newDelivery);
        if (!violations.isEmpty()) {
            log.error("Err: Contrain violation"+violations.toString());
            return this.violation();
        }
        dao.save(newDelivery);
        return this.EntityAdded();
    }

    /**
     * Endpoint to delete a specific delivery from a name
     *
     * @return a String
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String json) {
        ImplDelivery newDelivery = ImplDelivery.fromJson(json);
        if (newDelivery == null){
            return Response.status(402).build();
        }
        dao.delete(newDelivery);
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
