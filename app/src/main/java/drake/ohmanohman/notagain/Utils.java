package drake.ohmanohman.notagain;

import java.util.ArrayList;
import io.card.payment.CreditCard;

/**
 */
public class Utils {


    public static ArrayList<CreditCard> getCards(){
        // TODO actually read something
        ArrayList<CreditCard> cards = new ArrayList<CreditCard>();
        cards.add(new CreditCard("4111111111111111", 12, 2018, "456", "60054"));
        return cards;
    }

    public static void storeCards(ArrayList<CreditCard> cards){
        // TODO: actually store something
    }
}
