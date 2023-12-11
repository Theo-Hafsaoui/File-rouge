package univtln.hafsaoui.rouge.api;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.jpa.OrderDAO;
import univtln.hafsaoui.rouge.events.Producer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Path("/orders")
public class Orders implements Resource {

    @Inject OrderDAO dao;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpHeaders headers, @QueryParam("page") Integer page) {
        log.info("[]:request get with page"+page+" and headers:"+
                headers.getHeaderString("page"));
        if (headers.getHeaderString("page") == null) {
            List<Dto> orders = dao.getAllOf(page);
            return responseFrom(orders);
        } else{
            try {
                page = Integer.parseInt(headers.getHeaderString("page"));
                List<Dto> orders = dao.getAllOf(page);
                return responseFrom(orders);
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
     * Endpoint to put a specific order from a name
     *
     * @return a String
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String name) {
        return Response.status(501).build();
    }

    /**
     * Endpoint to delete a specific order from a name
     *
     * @return a String
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@Context HttpHeaders headers, String order) {
        Producer producer = Producer.of();
        producer.send_order(order,"ORDER-DELETE");
        return this.EntityAdded();
    }

    @Override
    public Response delete(String json) {
        return null;
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
