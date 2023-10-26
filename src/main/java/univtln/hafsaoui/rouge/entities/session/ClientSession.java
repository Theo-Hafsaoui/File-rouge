package univtln.hafsaoui.rouge.entities.session;

import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.entities.Client;
import univtln.hafsaoui.rouge.entities.Order;

import java.util.Set;

/**
 * Implementation of client using the collection
 */
@Getter
@Setter
public class ClientSession implements Client {
    private String name;
    private Set<Order> orders;
    private String firstname;
    private String email;

    /**
     * Static factory of ClientSession
     * @param name
     * @param firstname
     * @param email
     * @return
     */
    public static Client of(String name, String firstname, String email){
        return new ClientSession(name,firstname,email);
    }

    private ClientSession(String name, String firstname, String email) {
        this.name = name;
        this.firstname = firstname;
        this.email = email;
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

}
