package univtln.hafsaoui.rouge.api;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.jpa.ProductDAO;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Path("/products")
public class Products implements Resource {

    @Inject ProductDAO dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpHeaders headers, @QueryParam("page") Integer page) {
        log.info("[REST]:request get with page"+page+" and headers:"+
                headers.getHeaderString("page"));
        if (headers.getHeaderString("page") == null) {
            List<Dto> products = dao.getAllOf(page);
            return responseFrom(products);
        } else{
            try {
                page = Integer.parseInt(headers.getHeaderString("page"));
                List<Dto> products = dao.getAllOf(page);
                return responseFrom(products);
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
     * Endpoint to put a specific product from a name
     *
     * @return a String
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String name) {
        return Response.status(501).build();
    }

    /**
     * Endpoint to delete a specific product from a name
     *
     * @return a String
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(String name) {
        return Response.status(501).build();
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
