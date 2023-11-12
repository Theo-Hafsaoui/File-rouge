package univtln.hafsaoui.rouge.entities.session;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.entities.Client;
import univtln.hafsaoui.rouge.entities.Order;

import java.util.Set;

/**
 * Implementation of client using the collection
 */
@Getter
@Slf4j
@Setter
public class ClientSession implements Client {
    private String name;
    private Set<Order> orders;

    /**
     * Static factory of ClientSession
     * @param name
     * @return
     */
    public static Client of(String name){
        return new ClientSession(name);
    }

    private ClientSession(String name) {
        this.name = name;
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
