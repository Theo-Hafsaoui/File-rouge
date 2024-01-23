package univtln.hafsaoui.rouge.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.entities.interfaces.Delivery;
import univtln.hafsaoui.rouge.entities.interfaces.Order;
import univtln.hafsaoui.rouge.entities.interfaces.Product;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Implementation of Delvery using the collection
 */
@Setter
@Slf4j
@Getter
@Entity
@EqualsAndHashCode
@Table(name = "Delivery", schema = "red")
public class ImplDelivery implements Delivery {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int id;

    @Column(name = "Date")
    @PastOrPresent(message = "You cannot register a futur order")
    private Date date;

    @Column(name = "status")
    private String status;

    public ImplDelivery() {}

    public static ImplDelivery fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ImplDelivery delivery = objectMapper.readValue(json, ImplDelivery.class);
            return delivery;
        } catch (JsonProcessingException e) {
            log.error("Err: failed to dserialized "+e.getMessage());
            return null;
        }
    }

}