package univtln.hafsaoui.rouge.events;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import univtln.hafsaoui.rouge.daos.jpa.ClientDAO;
import univtln.hafsaoui.rouge.daos.jpa.OrderDAO;
import univtln.hafsaoui.rouge.daos.jpa.ProductDAO;
import univtln.hafsaoui.rouge.entities.ImplClient;
import univtln.hafsaoui.rouge.entities.ImplOrder;
import univtln.hafsaoui.rouge.entities.ImplProduct;


@Named( "Consumer" )
@Startup
@Singleton
@Slf4j
public class Consumer implements messageBroker{
    /**
     * Consumer class connected to Kafka that handle the new message for the current
     * JEE server
     */
    private KafkaConsumer<String, String> kafkaConsumer;
    @Inject
    ClientDAO clientDAO;
    @Inject
    ProductDAO productDAO;

    @Inject
    OrderDAO orderDAO;

    public Consumer() {
    }
    @PostConstruct
    public void subscribeToTopics() {
        this.kafkaConsumer = new KafkaConsumer<>(this.getProperty());
        kafkaConsumer.subscribe(messageBroker.TOPICS);
        new Thread(this::consumeMessages).start();
    }

    private void consumeMessages() {
        while (true) {//NOSONAR
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            records.forEach(record -> {
                    log.warn("Received message: " + record.value());
                    switch (record.key()){
                        case "CLIENT-PUT":
                            log.warn("Saving Client");
                            clientDAO.save(ImplClient.fromJson(record.value()));
                            break;
                        case "CLIENT-DELETE":
                            clientDAO.delete(ImplClient.fromJson(record.value()));
                            break;
                        case "PRODUCT-PUT":
                            productDAO.save(ImplProduct.fromJson(record.value()));
                            break;
                        case "PRODUCT-DELETE":
                            productDAO.delete(ImplProduct.fromJson(record.value()));
                            break;
                        case "ORDER-PUT":
                            orderDAO.save(ImplOrder.fromJson(record.value()));
                            break;
                        case "ORDER-DELETE":
                            orderDAO.delete(ImplOrder.fromJson(record.value()));
                            break;
                        default:
                            log.error("Err: Unknow key"+record.key());
                    }
                }
            );
        }
    }

}