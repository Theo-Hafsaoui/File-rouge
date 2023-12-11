package univtln.hafsaoui.rouge.events;


import java.util.List;
import java.util.Properties;

public interface messageBroker {

    final List<String> TOPICS = List.of("ORDERS",
                                        "CLIENTS",
                                        "PRODUCTS");

    default Properties getProperty() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
