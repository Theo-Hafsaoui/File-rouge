package univtln.hafsaoui.rouge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImplProductTest {

    @Test
    public void testFromJson() {
        String json = "{\"name\":\"nameOfAProduct\",\"description\":\"Best product\"}";
        ImplProduct product = ImplProduct.fromJson(json);
        assertNotNull(product);
        assertEquals("nameOfAProduct", product.getName());
        assertEquals("Best product", product.getDescription());
    }

}