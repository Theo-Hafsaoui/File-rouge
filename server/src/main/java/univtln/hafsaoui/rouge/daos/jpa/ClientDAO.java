package univtln.hafsaoui.rouge.daos.jpa;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dao;
import univtln.hafsaoui.rouge.daos.DaoMonitor;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.SimpleDto;
import univtln.hafsaoui.rouge.entities.interfaces.Client;
import jakarta.persistence.EntityManager;
import univtln.hafsaoui.rouge.entities.ImplClient;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
public class ClientDAO implements Dao<Client>, DaoMonitor, AutoCloseable{

    @PersistenceContext(unitName = "redPU")
    private EntityManager entityManager;
    private String get;
    @Resource(name= "java:app/jdbc/redDb")
    private DataSource datasource;
    private CriteriaQuery<ImplClient> all;

    @PostConstruct
    public void init() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ImplClient> criteriaBuilderQuery = criteriaBuilder.createQuery(ImplClient.class);
        Root<ImplClient> root = criteriaBuilderQuery.from(ImplClient.class);
        all = criteriaBuilderQuery.select(root);
        get = "SELECT c FROM ImplClient c WHERE c.name = :name";
    }

    @Override
    public Optional<Dto> get(String name) {
        log.warn("[DAO] finder called for %s",name);
        Client client = entityManager.createQuery(get, ImplClient.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        if(client == null){ return Optional.empty(); }
        return Optional.of(new SimpleDto(client.getName(),""));

    }

    @Override
    public List<Dto> getAllOf(Integer page) {
        if (page == null) {
            page =0;
        }
        TypedQuery<ImplClient> query = entityManager.createQuery(all);
        int offset = (page) * Dao.PAGE_SIZE;
        query.setFirstResult(offset);
        query.setMaxResults(Dao.PAGE_SIZE);
        return query.getResultList().stream().map(client -> {
            return (Dto) new SimpleDto(client.getName(), client.getDescription());
        }).toList();
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