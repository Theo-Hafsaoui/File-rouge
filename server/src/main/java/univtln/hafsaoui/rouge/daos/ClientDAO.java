package univtln.hafsaoui.rouge.daos;

import jakarta.persistence.TypedQuery;
import univtln.hafsaoui.rouge.entities.Client;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ClientDAO implements Dao<Client>, AutoCloseable{
    private final EntityManager entityManager;

    /**
     * Private construcator of ClientDAO
     *
     * @param entityManager
     */
    private ClientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Static factory of ClientDAO, Take an entity manager
     * and return a clientDAO to manipulate the database
     *
     * @param entityManager
     * @return dao
     */
    public static ClientDAO of(EntityManager entityManager) {
        return new ClientDAO(entityManager);
    }

    @Override
    public Optional<Client> get(String name) {
        TypedQuery<Client> query = entityManager.createQuery(
                "SELECT T FROM ClientPersist as T WHERE name=name ",
                Client.class);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Client> getAll() {
        TypedQuery<Client> query = entityManager.createQuery("SELECT T FROM ClientPersist as T", Client.class);
        return query.getResultList();
    }

    @Override
    public void save(Client client) {
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Client client) {
        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Client client) {
        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
    }

}
