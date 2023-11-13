package univtln.hafsaoui.rouge.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import jakarta.ws.rs.ApplicationPath;

import java.util.List;

@ApplicationScoped
@ApplicationPath("/api")
@Path("/health")
@Slf4j
public class Api extends Application {

    /**
     * Take an entity and return a response from it
     * @param entity
     * @return
     */
    private Response responseFrom(List<Object> entity) {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(entity.toString())
                .build();
    }

    /**
     * Endpoint to get an enity
     *
     * @return a String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthCheck() {
        return Response.noContent().build();
    }

}
