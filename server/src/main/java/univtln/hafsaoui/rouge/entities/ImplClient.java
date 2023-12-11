package univtln.hafsaoui.rouge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.entities.annotation.LegalCountry;
import univtln.hafsaoui.rouge.entities.interfaces.Client;
import univtln.hafsaoui.rouge.entities.interfaces.Order;

import java.util.Set;

/**
 * Implementation of client using the collection
 */
@Getter
@Slf4j
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "Country", schema = "red")
public class ImplClient implements Client {
    @Id
    @Column(name = "name", unique = true, nullable = false, length = 255)
    @NotNull(message = "Name cannot be null")
    @LegalCountry
    private String name;
    private String description;
    @Transient
    private Set<Order> orders;

    @JsonIgnore
    public Set<Order> getOrders() {
        return orders != null ? Set.copyOf(orders) : null;
    }


    /**
     * Static factory of ClientSession
     * @param name
     * @return
     */
    public static ImplClient of(String name){
        return new ImplClient(name);
    }

    public ImplClient() { }

    private ImplClient(String name) {
        this.name = name;
    }

    /**
     * Take an order and add it o the order of this
     * Client
     * @param order
     */
    @Override
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @Override
    public Boolean isValid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        validator.validate(this).forEach(violation ->
                log.warn(violation.getMessage())
        );
        return validator.validate(this).isEmpty();
    }

    @Override
    public String getJson() {
        return "{\"name\":\"" + this.name +
                "\",\"description\": \"" + this.description +
                "\"}";
    }

    public static ImplClient fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ImplClient client = objectMapper.readValue(json, ImplClient.class);
            return client;
        } catch (JsonProcessingException e) {
            log.error("Err: failed to dserialized "+e.getMessage());
            return null;
        }
    }
}
