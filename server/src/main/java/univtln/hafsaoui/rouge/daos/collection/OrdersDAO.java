package univtln.hafsaoui.rouge.daos.collection;

import jakarta.enterprise.inject.Alternative;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dao;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.OrderDto;
import univtln.hafsaoui.rouge.daos.SimpleDto;
import univtln.hafsaoui.rouge.entities.interfaces.Order;

import java.util.List;
import java.util.Optional;

@Slf4j
@Alternative
public class OrdersDAO implements Dao<Order>{

    /**
     * Private construcator of OrderDAO using collection
     *
     */
    private OrdersDAO() {
    }


    /**
     * Static factory of OrderDAO, Take nothing and return
     * something useless
     *
     * @return dao
     */
    public static OrdersDAO of() {
        return new OrdersDAO();
    }

    /**
     * Take a product and return the first orders with this product
     * inside the session set of orders
     *
     * @param name
     * @return
     */
    @Override
    public Optional<Dto> get(String name) {
        return null;
    }

    @Override
    public List<Dto> getAllOf(Integer page) {
        return Dao.setOrders.stream().map(order -> {
            return (Dto) new OrderDto(order.getSupplier().getName(),
                                      order.getRecipient().getName(),
                                      0,
                                      order.getProduct().getName(),
                                      order.getDate());
        }).toList();
    }

    @Override
    public void save(Order order) {
        Dao.setOrders.add(order);
    }

    @Override
    public void update(Order order) {
        Optional<Order> targetOfUpdate = Optional.ofNullable(Dao.setOrders.stream()
                .filter(iOrder -> iOrder.getSupplier().getName().equals(order.getSupplier().getName()))
                .toList().get(0));
        if (targetOfUpdate.isPresent()){
            Dao.setOrders.remove(targetOfUpdate.get());
            Dao.setOrders.add(order);
        }

    }

    @Override
    public void delete(Order order) {
        Dao.setOrders.remove(order);
    }

}
