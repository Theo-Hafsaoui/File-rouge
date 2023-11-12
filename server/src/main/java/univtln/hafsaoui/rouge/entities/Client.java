package univtln.hafsaoui.rouge.entities;

import java.util.Set;

public interface Client {
    Set<Order> getOrders();
    String getName();

    void addOrder(Order order);
    void setName(String name);

}
