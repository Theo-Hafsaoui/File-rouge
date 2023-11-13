package univtln.hafsaoui.rouge.daos.jpa;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import univtln.hafsaoui.rouge.daos.Dao;
import univtln.hafsaoui.rouge.daos.DaoMonitor;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.SimpleDto;
import univtln.hafsaoui.rouge.entities.ImplProduct;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductDAO implements Dao<Product>, DaoMonitor, AutoCloseable{

    @PersistenceContext(unitName = "redPU")
    private EntityManager entityManager;
    private TypedQuery<ImplProduct> getProducts;
    private CriteriaQuery<ImplProduct> all;
    private String get;

    @PostConstruct
    public void init() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ImplProduct> criteriaBuilderQuery = criteriaBuilder.createQuery(ImplProduct.class);
        Root<ImplProduct> root = criteriaBuilderQuery.from(ImplProduct.class);
        all = criteriaBuilderQuery.select(root);
        get = "SELECT p FROM Product c WHERE p.name = :name";
    }

    @Override
    public Optional<Dto> get(String name) {
        Product product = entityManager.createQuery(get, Product.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        if(product == null){ return Optional.empty(); }
        return Optional.ofNullable(new SimpleDto(
                product.getName(),""
        ));
    }

    @Override
    public List<Dto> getAllOf(Integer page) {
        getProducts = entityManager.createQuery(all);
        int offset = (page) * Dao.PAGE_SIZE;
        getProducts.setFirstResult(offset);
        getProducts.setMaxResults(Dao.PAGE_SIZE);
        return getProducts.getResultList().stream().map(product -> {
            return (Dto) new SimpleDto(product.getName(), product.getDescription());
        }).toList();
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