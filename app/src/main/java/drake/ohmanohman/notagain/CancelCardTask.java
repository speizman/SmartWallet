package drake.ohmanohman.notagain;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import io.card.payment.CreditCard;

/**
 */
public class CancelCardTask implements java.lang.Runnable {

    // Card
    private CreditCard card;
    private JSONObject fraudulentBase;

    // Constants
    private static String feedzaiBase = "https://sandbox.feedzai.com/v1/payments";

    public CancelCardTask(CreditCard card, JSONObject fraudulentBase){
        this.card = card;
        this.fraudulentBase = fraudulentBase;
    }

    @Override
    public void run()  {
        Log.e("err", "herro2");
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(feedzaiBase).openConnection() ;
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic MDE1NDQ2MWY1ZTU0YTQ4ZTAwMDAwMDAwN2NkZDM2ZWExMmVkNTEwZDlkNGY5OWJhMDVlYjIwNmI1N2NhYzZmNTo=:");
            connection.connect();
            OutputStream stream = connection.getOutputStream();
            stream.write(fraudulentBase.toString().getBytes());
            stream.flush(); stream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null)
                buffer.append(line);
            Log.e("err response: ", buffer.toString());
        } catch(Exception e) {
            Log.e("err", e.toString());
        }
    }
}
