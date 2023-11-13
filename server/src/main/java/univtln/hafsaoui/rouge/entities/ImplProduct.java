package univtln.hafsaoui.rouge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import univtln.hafsaoui.rouge.daos.jpa.ProductDAO;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

/**
 * Implementation of product using the collection
 */
@Getter
@Setter
@Entity
@Table(name = "Product", schema = "red")
public class ImplProduct implements Product {
    @Id
    @Column(name = "name", unique = true, nullable = false, length = 255)
    @NotNull(message = "Name cannot be null")
    private String name;
    private String description;

    /**
     * Static factory of ProductSession
     * @param name
     * @return
     */
    public static Product of(String name){
        return new ImplProduct(name);
    }

    public ImplProduct() { }

    private ImplProduct(String name) {
        this.name = name;
    }

}
