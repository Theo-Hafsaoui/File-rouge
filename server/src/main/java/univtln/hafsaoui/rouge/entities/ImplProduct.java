package univtln.hafsaoui.rouge.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

/**
 * Implementation of product using the collection
 */
@Getter
@Slf4j
@Setter
@Entity
@EqualsAndHashCode
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

    @Override
    public String getJson() {
        return "{\"name\":\"" + this.name +
                "\",\"description\": \"" + this.description +
                "\"}";
    }

    public static ImplProduct fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ImplProduct product = objectMapper.readValue(json, ImplProduct.class);
            return product;
        } catch (JsonProcessingException e) {
            log.error("Err: failed to dserialized "+e.getMessage());
            return null;
        }
    }

}