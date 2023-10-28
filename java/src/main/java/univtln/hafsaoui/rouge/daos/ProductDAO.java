package univtln.hafsaoui.rouge.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import univtln.hafsaoui.rouge.entities.Product;

import java.util.List;
import java.util.Optional;

public class ProductDAO implements Dao<Product>, AutoCloseable{
    private final EntityManager entityManager;

    /**
     * Private construcator of ProductDAO
     *
     * @param entityManager
     */
    private ProductDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Static factory of ProductDAO, Take an entity manager
     * and return a productDAO to manipulate the database
     *
     * @param entityManager
     * @return dao
     */
    public static ProductDAO of(EntityManager entityManager) {
        return new ProductDAO(entityManager);
    }

    @Override
    public Optional<Product> get(String name) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT T FROM ProductPersist as T WHERE name=name ",
                Product.class);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Product> getAll() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT T FROM ProductPersist as T", Product.class);
        return query.getResultList();
    }

    @Override
    public void save(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Product product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Product product) {
        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
    }

}
