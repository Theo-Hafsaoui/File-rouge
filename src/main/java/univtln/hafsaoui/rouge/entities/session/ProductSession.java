package univtln.hafsaoui.rouge.entities.session;

import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.entities.Product;
import univtln.hafsaoui.rouge.entities.RowOrder;

import java.util.Date;

/**
 * Implementation of Product using the collection
 */
@Getter
@Setter
public class ProductSession implements Product {
    int id;
    int price;
    RowOrder rowOrder;

    /**
     * Static factory of ProductSession
     * @param id
     * @param price
     * @param rowOrder
     * @return
     */
    public static ProductSession of(int id, int price, RowOrder rowOrder){
        return new ProductSession(id,price,rowOrder);
    }

    private ProductSession(int id, int price, RowOrder rowOrder) {
        this.id = id;
        this.price = price;
        this.rowOrder = rowOrder;
    }
}
