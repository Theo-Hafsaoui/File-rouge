package univtln.hafsaoui.rouge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ImplClientTest {

    @Test
    public void testFromJson() {
        String json = "{\"name\":\"nameOfACountry\",\"description\":\"Best countryy\"}";
        ImplClient client = ImplClient.fromJson(json);
        assertNotNull(client);
        assertEquals("nameOfACountry", client.getName());
        assertEquals("Best countryy", client.getDescription());
    }

}