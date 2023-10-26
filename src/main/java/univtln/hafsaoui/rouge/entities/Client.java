package univtln.hafsaoui.rouge.entities;

import java.util.Set;

public interface Client {
    String getEmail();
    Set<Order> getOrders();
    String getName();
    String getFirstname();

    void addOrder(Order order);
    void setEmail(String email);
    void setName(String name);
    void setFirstname(String firstname);

}
