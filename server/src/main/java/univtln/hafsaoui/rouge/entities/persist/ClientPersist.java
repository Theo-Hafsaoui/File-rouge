package univtln.hafsaoui.rouge.entities.persist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.daos.ClientDAO;
import univtln.hafsaoui.rouge.entities.Client;
import univtln.hafsaoui.rouge.entities.Order;

import java.util.List;
import java.util.Set;

/**
 * Implementation of client using the collection
 */
@Getter
@Setter
@Entity
@Table(name = "country", schema = "red")
public class ClientPersist implements Client {
    @Id
    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name;
    private String description;
    @Transient
    private Set<Order> orders;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ClientDAO dao;

    /**
     * Static factory of ClientSession
     * @param name
     * @return
     */
    public static Client of(String name){
        return new ClientPersist(name);
    }

    public ClientPersist() { }

    private ClientPersist(String name) {
        this.name = name;
        emf = Persistence.createEntityManagerFactory("redPU");
        em = emf.createEntityManager();
        dao = ClientDAO.of(em);
        dao.save(this);
    }

    public static List<Client> getAll() {
        emf = Persistence.createEntityManagerFactory("redPU");
        em = emf.createEntityManager();
        dao = ClientDAO.of(em);
        return  dao.getAll();
    }

    /**
     * Take an order and add it o the order of this
     * Client
     * @param order
     */
    @Override
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Override
    public String toString() {
        return "ClientPersist{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", orders=" + orders +
                '}';
    }
}
