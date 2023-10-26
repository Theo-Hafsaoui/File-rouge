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
    int id;
    Date date;
    Client customer;
    private Set<Product> products;

    /**
     * Static factory of ProductSession
     * @param id
     * @param date
     * @param customer
     * @return
     */
    public static Order of(int id, Date date, Client customer){
        return new OrderSession(id ,date,customer);
    }

   private OrderSession(int id, Date date, Client customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    @Override
    public void setProducts(Collection<Product> products) {
        this.products = new HashSet<>(products);
    }

}