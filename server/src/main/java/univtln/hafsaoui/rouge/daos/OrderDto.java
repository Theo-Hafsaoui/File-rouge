package univtln.hafsaoui.rouge.daos;
import java.util.*;


public record OrderDto(String supplier, String recipient, int number, String product, Date date) implements Dto {


    @Override
    public String toString() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(this.date);
        return "{\"Date\":\"" + cal.get(Calendar.YEAR) +
                "\",\"nb\": \"" + this.number+
                "\",\"Product\": \"" + this.product+
                "\",\"Suplier\": \"" + this.supplier +
                "\", \"Recipient\": \"" + this.recipient +
                "\"}";

    }

}
