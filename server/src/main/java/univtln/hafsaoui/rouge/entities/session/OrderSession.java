package univtln.hafsaoui.rouge.entities.session;

import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.entities.Client;
import univtln.hafsaoui.rouge.entities.Order;
import univtln.hafsaoui.rouge.entities.Product;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of Order using the collection
 */
@Setter
@Getter
public class OrderSession implements Order {
    Date date;
    Client supplier;
    Client recipient;
    private Set<Product> products;

    /**
     * Static factory of ProductSession
     * @param date
     * @param supplier
     * @param recipient
     * @return
     */
    public static Order of( Date date, Client supplier, Client recipient){
        return new OrderSession(date,supplier,recipient);
    }

    private OrderSession(Date date, Client supplier,Client recipient) {
        this.date = date;
        this.supplier = supplier;
        this.recipient = recipient;
    }

    @Override
    public void setProducts(Collection<Product> products) {
        this.products = new HashSet<>(products);
    }

}