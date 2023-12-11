package univtln.hafsaoui.rouge.events;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import univtln.hafsaoui.rouge.entities.interfaces.Client;
import univtln.hafsaoui.rouge.entities.interfaces.Order;


@Slf4j
public class Producer implements messageBroker {

    private KafkaProducer PRODUCER;

    public Producer() {
        PRODUCER = new org.apache.kafka.clients.producer.KafkaProducer<>(this.getProperty());
    }

    public static Producer of() {
        return new Producer();
    }

    /**
     * Publish an order to kafka
     */
    public void publish (Order order){

    }

    public void send_order(String order_json, String order){
        String TOPIC = "CLIENTS";
        this.send(TOPIC, order, order_json);
    }

    public void send_client(Client client, String order){
        String TOPIC = "CLIENTS";
        this.send(TOPIC, order, client.getJson());
    }


    private void send(String topic,String key, String msg){
            ProducerRecord<String, String> record = new ProducerRecord<>(topic,key,msg);
            PRODUCER.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        log.error("Error sending message: " + exception.getMessage());
                    } else {
                        log.info("Message sent successfully! Topic: " + metadata.topic() +
                                ", Partition: " + metadata.partition() +
                                ", Offset: " + metadata.offset());
                    }
                }
            });
            PRODUCER.flush();
            PRODUCER.close();
    }

}