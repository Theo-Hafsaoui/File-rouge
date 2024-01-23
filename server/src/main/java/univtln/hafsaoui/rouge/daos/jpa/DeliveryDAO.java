package univtln.hafsaoui.rouge.daos.jpa;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dao;
import univtln.hafsaoui.rouge.daos.DaoMonitor;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.SimpleDto;
import univtln.hafsaoui.rouge.entities.ImplDelivery;
import univtln.hafsaoui.rouge.entities.interfaces.Delivery;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class DeliveryDAO implements Dao<Delivery>, DaoMonitor, AutoCloseable{

    @PersistenceContext(unitName = "redPU")
    private EntityManager entityManager;
    private String get;
    @Resource(name= "java:app/jdbc/redDb")
    private DataSource datasource;
    private CriteriaQuery<ImplDelivery> all;

    @PostConstruct
    public void init() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ImplDelivery> criteriaBuilderQuery = criteriaBuilder.createQuery(ImplDelivery.class);
        Root<ImplDelivery> root = criteriaBuilderQuery.from(ImplDelivery.class);
        all = criteriaBuilderQuery.select(root);
        get = "SELECT c FROM ImplDelivery c WHERE c.name = :name";
    }

    @Override
    public Optional<Dto> get(String name) {
        log.warn("[DAO] finder called for %s",name);
        Delivery delivery = entityManager.createQuery(get, ImplDelivery.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        if(delivery == null){ return Optional.empty(); }
        return Optional.of(new SimpleDto(delivery.toString(),""));

    }

    @Override
    public List<Dto> getAllOf(Integer page) {
        TypedQuery<ImplDelivery> query = entityManager.createQuery("SELECT T FROM ImplDelivery as T",ImplDelivery.class);
        return query.getResultList().stream().map(delivery -> {
            return (Dto) new SimpleDto(delivery.getStatus(),delivery.getDate().toString());
        }).toList();
    }

    @Override
    public void save(Delivery delivery) {
        entityManager.persist(delivery);
    }

    @Override
    public void update(Delivery delivery) {
        entityManager.merge(delivery);
    }

    @Override
    public void delete(Delivery delivery) {
        entityManager.remove(delivery);
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
    }

}