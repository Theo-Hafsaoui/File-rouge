package univtln.hafsaoui.rouge.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.entities.interfaces.Order;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

import java.util.*;

/**
 * Implementation of Order using the collection
 */
@Setter
@Slf4j
@Getter
@Entity
@EqualsAndHashCode
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

    public static ImplOrder fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ImplOrder order = objectMapper.readValue(json, ImplOrder.class);
            return order;
        } catch (JsonProcessingException e) {
            log.error("Err: failed to dserialized "+e.getMessage());
            return null;
        }
    }

    @Override
    public void setProducts(Collection<Product> products) {
        this.products = new HashSet<>(products);
    }

}