package univtln.hafsaoui.rouge.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface Order {
   int getId ();
    Date getDate ();
    void setId (int id);
    void setProducts (Collection<Product> products);
    void setDate (Date date);
}
