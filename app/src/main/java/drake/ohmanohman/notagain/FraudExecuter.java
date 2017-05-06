package drake.ohmanohman.notagain;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.card.payment.CreditCard;

public class FraudExecuter {

    // Async network threading
    private ThreadPoolExecutor executor;
    private ArrayList<CreditCard> cards;
    private Context activity;

    private static JSONObject baseFraudulentTransactionJSON ;

    public FraudExecuter(Context c) {
        BlockingQueue<Runnable> runner = new LinkedBlockingQueue<>();
        this.executor = new ThreadPoolExecutor(4, 8, 50000l, TimeUnit.MILLISECONDS, runner);
        this.activity = c;
        // Start json loading
        executor.execute(loadBaseFraud);
        // Get all stored credit cards
        cards = Utils.getCards();
    }

    public void executeFraud()  {
        for(CreditCard card : cards) {
            executor.execute(new CancelCardTask(card, baseFraudulentTransactionJSON));
            executor.execute(new CancelCardTask(card, baseFraudulentTransactionJSON));
            executor.execute(new CancelCardTask(card, baseFraudulentTransactionJSON));
            executor.execute(new CancelCardTask(card, baseFraudulentTransactionJSON));
            executor.execute(new CancelCardTask(card, baseFraudulentTransactionJSON));
        }
    }

    private Runnable loadBaseFraud = new Runnable() {
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(activity.getAssets().open("baseFraudulentTransaction.json")));

                // do reading, usually loop until end of file reading
                StringBuffer buffer = new StringBuffer();
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    buffer.append(mLine);
                }
                baseFraudulentTransactionJSON = new JSONObject(buffer.toString());
            } catch (Exception e) {
                Log.e("err", e.getMessage());
            }
        }
    };
}
