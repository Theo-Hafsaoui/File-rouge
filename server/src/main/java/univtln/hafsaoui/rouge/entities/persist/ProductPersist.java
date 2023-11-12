package univtln.hafsaoui.rouge.entities.persist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.daos.ProductDAO;
import univtln.hafsaoui.rouge.entities.Product;
import univtln.hafsaoui.rouge.entities.Order;

import java.util.List;
import java.util.Set;

/**
 * Implementation of product using the collection
 */
@Getter
@Setter
@Entity
@Table(name = "Product", schema = "red")
public class ProductPersist implements Product {
    @Id
    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name;
    private String description;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ProductDAO dao;

    /**
     * Static factory of ProductSession
     * @param name
     * @return
     */
    public static Product of(String name){
        return new ProductPersist(name);
    }

    public ProductPersist() { }

    private ProductPersist(String name) {
        this.name = name;
        emf = Persistence.createEntityManagerFactory("redPU");
        em = emf.createEntityManager();
        dao = ProductDAO.of(em);
        dao.save(this);
    }

    public static List<Product> getAll() {
        emf = Persistence.createEntityManagerFactory("redPU");
        em = emf.createEntityManager();
        dao = ProductDAO.of(em);
        return  dao.getAll();
    }

    @Override
    public String toString() {
        return "ProductPersist{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
