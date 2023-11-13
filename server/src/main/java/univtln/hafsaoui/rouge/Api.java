package univtln.hafsaoui.rouge;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.ProductDAO;
import univtln.hafsaoui.rouge.daos.ClientDAO;
import univtln.hafsaoui.rouge.daos.Dao;
import univtln.hafsaoui.rouge.daos.OrderDAO;
import jakarta.ws.rs.ApplicationPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
@ApplicationPath("/api")
@Path("/test")
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
    @Path("{entity}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt(@PathParam("entity") String entity) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("redPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Object> entity_from_db=new ArrayList<>();
        Dao dao ;
        switch (entity) {
            case "Clients":
                entity_from_db = Collections.singletonList(
                        ClientDAO.of(entityManager).getAll());
                return responseFrom(entity_from_db);
            case "Products":
                entity_from_db = Collections.singletonList(
                        ProductDAO.of(entityManager).getAll());
                return responseFrom(entity_from_db);
            case "Orders":
                entity_from_db = Collections.singletonList(
                        OrderDAO.of(entityManager).getAll());
                return responseFrom(entity_from_db);
            default:
                return Response.status(404,"No such entity").build();
        }
    }

}
