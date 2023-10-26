package univtln.hafsaoui.rouge.entities.session;

import org.junit.Test;
import univtln.hafsaoui.rouge.entities.Client;
import univtln.hafsaoui.rouge.entities.Order;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

public class ClientSessionTest {

    @Test
    public void shouldCreatACLient(){
        Client c = ClientSession.of("test","test","test");
        assertNotNull(c);
    }

    @Test
    public void shouldAddAndOrder(){
        Client customer = ClientSession.of("test","test","test");
        Order o = OrderSession.of(42, new Date(),customer);
        assertNotNull(o);
    }


}