package univtln.hafsaoui.rouge;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import univtln.hafsaoui.rouge.daos.ClientDAO;
import univtln.hafsaoui.rouge.daos.OrderDAO;
import univtln.hafsaoui.rouge.daos.ProductDAO;

@Path("red")
public class Api {
    /**
     * Endpoint to test the health of the server
     *
     * @return a String
     */
    @GET
    @Path("/{entity}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt(@PathParam("entity") String entity) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("redPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        switch (entity) {
            case "Clients":
                return Response
                        .status(200)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .header("Access-Control-Max-Age", "1209600")
                        .entity( ClientDAO.of(entityManager).getAll().toString())
                        .build();
            case "Products":
                return Response.status(404,"No such entity yet").build();
            case "Orders":
                return Response
                        .status(200)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                        .header("Access-Control-Max-Age", "1209600")
                        .entity( OrderDAO.of(entityManager).getAll().toString())
                        .build();
            default:
                return Response.status(404,"No such entity").build();
        }
    }

}
