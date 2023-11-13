package univtln.hafsaoui.rouge.entities.interfaces;

import java.util.Collection;
import java.util.Date;

public interface Order {
    Client getSupplier ();
    Client getRecipient ();
    Product getProduct ();
    Date getDate ();
    void setProducts (Collection<Product> products);
    void setDate (Date date);
}
