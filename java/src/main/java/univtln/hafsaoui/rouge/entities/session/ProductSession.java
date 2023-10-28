package univtln.hafsaoui.rouge.entities.session;

import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.entities.Product;

/**
 * Implementation of Product using the collection
 */
@Getter
@Setter
public class ProductSession implements Product {

    String name;
    String description;
    /**
     * Static factory of ProductSession
     * @param name
     * @param description
     * @return
     */
    public static ProductSession of(String name,String description){
        return new ProductSession(name,description);
    }

    private ProductSession(String name,String description) {
        this.name = name;
        this.description = description;
    }

}
