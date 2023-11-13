package univtln.hafsaoui.rouge.entities.persist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.entities.Client;
import univtln.hafsaoui.rouge.entities.Order;
import univtln.hafsaoui.rouge.entities.Product;

import java.util.*;

/**
 * Implementation of Order using the collection
 */
@Setter
@Getter
@Entity
@Table(name = "Order", schema = "red")


public class OrderPersist implements Order {
    @Id
    @Column(name = "Date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Product")
    private ProductPersist product;

    @ManyToOne
    @JoinColumn(name = "Suplier")
    private ClientPersist supplier;

    @ManyToOne
    @JoinColumn(name = "Recipient")
    private ClientPersist recipient;

    @Transient
    private Set<Product> products;

    /**
     * Static factory of ProductSession
     * @param date
     * @param supplier
     * @param recipient
     * @return
     */
    public static Order of( Date date, ClientPersist supplier, ClientPersist recipient){
        return new OrderPersist(date,supplier,recipient);
    }

    public OrderPersist() {}

    private OrderPersist(Date date, ClientPersist supplier, ClientPersist recipient) {
        this.date = date;
        this.supplier = supplier;
        this.recipient = recipient;
    }

    @Override
    public void setProducts(Collection<Product> products) {
        this.products = new HashSet<>(products);
    }

    @Override
    public String toString() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        return "{\"Date\":\"" + cal.get(Calendar.YEAR) +
                "\",\"Product\": \"" + product.getName() +
                "\",\"Suplier\": \"" + supplier.getName() +
                "\", \"Recipient\": \"" + recipient.getName() +
                "\"}";

    }
}