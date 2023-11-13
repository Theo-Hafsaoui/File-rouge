package univtln.hafsaoui.rouge.daos;

import univtln.hafsaoui.rouge.entities.interfaces.Client;
import univtln.hafsaoui.rouge.entities.interfaces.Order;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Dao<T> {
    static Set<Order> setOrders = new HashSet<>();
    static Set<Product> setProducts = new HashSet<>();
    static Set<Client> setClients = new HashSet<>();
    final int PAGE_SIZE = 50;
    Optional<Dto> get(String primaryKey);

    List<Dto> getAllOf(Integer pageSize);

    void save(T t);

    void update(T t);

    void delete(T t);
}
