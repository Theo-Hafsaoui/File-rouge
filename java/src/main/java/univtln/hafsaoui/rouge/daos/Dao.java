package univtln.hafsaoui.rouge.daos;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(String primaryKey);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}
