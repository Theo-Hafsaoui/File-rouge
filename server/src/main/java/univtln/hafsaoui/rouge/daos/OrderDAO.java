package univtln.hafsaoui.rouge.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import univtln.hafsaoui.rouge.entities.Order;

import java.util.List;
import java.util.Optional;

public class OrderDAO implements Dao<Order>, AutoCloseable{
    private final EntityManager entityManager;

    /**
     * Private construcator of OrderDAO
     *
     * @param entityManager
     */
    private OrderDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Static factory of OrderDAO, Take an entity manager
     * and return a orderDAO to manipulate the database
     *
     * @param entityManager
     * @return dao
     */
    public static OrderDAO of(EntityManager entityManager) {
        return new OrderDAO(entityManager);
    }

    @Override
    public Optional<Order> get(String productName) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT T FROM OrderPersist as T WHERE T.product = :productName",
                Order.class);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Order> getAll() {
        TypedQuery<Order> query = entityManager.createQuery("SELECT T FROM OrderPersist as T", Order.class);
        return query.getResultList();
    }

    @Override
    public void save(Order order) {
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Order order) {
        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Order order) {
        entityManager.getTransaction().begin();
        entityManager.remove(order);
        entityManager.getTransaction().commit();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
    }

}
