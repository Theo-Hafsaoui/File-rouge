package univtln.hafsaoui.rouge.entities;

import java.util.Collection;
import java.util.Date;

public interface Order {
    Client getSupplier ();
    Client getRecipient ();
    Date getDate ();
    void setProducts (Collection<Product> products);
    void setDate (Date date);
}
