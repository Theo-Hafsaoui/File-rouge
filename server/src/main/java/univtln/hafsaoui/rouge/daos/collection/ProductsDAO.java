package univtln.hafsaoui.rouge.daos.collection;

import jakarta.enterprise.inject.Alternative;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dao;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.SimpleDto;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

import java.util.List;
import java.util.Optional;

@Slf4j
@Alternative
public class ProductsDAO implements Dao<Product>{

    /**
     * Private construcator of ProductDAO using collection
     *
     */
    private ProductsDAO() {
    }


    /**
     * Static factory of ProductDAO, Take nothing and return
     * something useless
     *
     * @return dao
     */
    public static ProductsDAO of() {
        return new ProductsDAO();
    }

    @Override
    public Optional<Dto> get(String name) {
        return null;
    }

    @Override
    public List<Dto> getAllOf(Integer page) {
        return Dao.setProducts.stream().map(product -> {
            return (Dto) new SimpleDto(product.getName(),"");
        }).toList();
    }

    @Override
    public void save(Product product) {
        Dao.setProducts.add(product);
    }

    @Override
    public void update(Product product) {
        Optional<Product> targetOfUpdate = Optional.ofNullable(Dao.setProducts.stream()
                .filter(iProduct -> iProduct.getName().equals(product.getName()))
                .toList().get(0));
        if (targetOfUpdate.isPresent()){
            Dao.setProducts.remove(targetOfUpdate.get());
            Dao.setProducts.add(product);
        }

    }

    @Override
    public void delete(Product product) {
        Dao.setProducts.remove(product);
    }

}
