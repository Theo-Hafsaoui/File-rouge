package univtln.hafsaoui.rouge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.entities.interfaces.Order;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

import java.util.*;

/**
 * Implementation of Order using the collection
 */
@Setter
@Getter
@Entity
@Table(name = "Order", schema = "red")
public class ImplOrder implements Order {
    @Id
    @Column(name = "Date")
    @PastOrPresent(message = "You cannot register a futur order")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Product")
    private ImplProduct product;

    @ManyToOne
    @JoinColumn(name = "Suplier")
    private ImplClient supplier;

    @ManyToOne
    @JoinColumn(name = "Recipient")
    private ImplClient recipient;

    @Transient
    private Set<Product> products;

    /**
     * Static factory of ProductSession
     *
     * @param date
     * @param supplier
     * @param recipient
     * @return
     */
    public static Order of(Date date, ImplClient supplier, ImplClient recipient) {
        return new ImplOrder(date, supplier, recipient);
    }

    public ImplOrder() {
    }

    private ImplOrder(Date date, ImplClient supplier, ImplClient recipient) {
        this.date = date;
        this.supplier = supplier;
        this.recipient = recipient;
    }


    @Override
    public void setProducts(Collection<Product> products) {
        this.products = new HashSet<>(products);
    }

}