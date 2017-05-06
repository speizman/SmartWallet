package drake.ohmanohman.notagain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vinaygaba.creditcardview.CreditCardView;

import io.card.payment.CreditCard;

/**
 */
public class CreditCardAdapter extends ArrayAdapter<CreditCard> {

    public View currentView;
    public CreditCardAdapter(Context context){
        super(context, R.layout.card);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        this.currentView = convertView;
        if (currentView == null)
            currentView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.card, parent, false);

        // Fill credit card
        CreditCard card = getItem(pos);
        CreditCardView currentCard = (CreditCardView) currentView.findViewById(R.id.card1);
        currentCard.setCardNumber(card.cardNumber);
        Log.e("err", currentCard.getCardNumber());
        currentCard.setExpiryDate(card.expiryMonth + "/" + card.expiryYear);
        currentCard.setIsEditable(true);
        currentCard.setCardName("Vishnu Indukuri");
        currentCard.putChip(false);
        currentView.invalidate();
        return currentView;
    }
}
