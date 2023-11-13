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
import univtln.hafsaoui.rouge.daos.OrderDto;
import univtln.hafsaoui.rouge.entities.ImplOrder;
import univtln.hafsaoui.rouge.entities.interfaces.Order;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class OrderDAO implements Dao<Order>, DaoMonitor, AutoCloseable{

    @PersistenceContext(unitName = "redPU")
    private EntityManager entityManager;

    private CriteriaQuery<ImplOrder> all;
    private String get;

    @PostConstruct
    public void init() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ImplOrder> criteriaBuilderQuery = criteriaBuilder.createQuery(ImplOrder.class);
        Root<ImplOrder> root = criteriaBuilderQuery.from(ImplOrder.class);
        all = criteriaBuilderQuery.select(root);
        get = "SELECT o FROM Order c WHERE o.name = :name";
    }


    @Override
    public Optional<Dto> get(String productName) {


        return null;
    }

    @Override
    public List<Dto> getAllOf(Integer page) {
        TypedQuery<ImplOrder> query = entityManager.createQuery(all);
        int offset = (page) * Dao.PAGE_SIZE;
        query.setFirstResult(offset);
        query.setMaxResults(Dao.PAGE_SIZE);
        return query.getResultList().stream().map(order -> {
            return (Dto) new OrderDto(order.getSupplier().getName(),
                                order.getRecipient().getName(),
                        0,
                                order.getProduct().getName(),
                                order.getDate());
        }).toList();
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
