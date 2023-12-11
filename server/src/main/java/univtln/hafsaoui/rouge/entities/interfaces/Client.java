package univtln.hafsaoui.rouge.entities.interfaces;

import java.util.Set;

public interface Client {
    Boolean isValid();
    Set<Order> getOrders();
    String getName();

    void addOrder(Order order);
    void setName(String name);

    String getJson();

}
