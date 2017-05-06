package drake.ohmanohman.notagain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 */
public class CardActivity extends Activity {


    private ListView cards_view;
    private CreditCardAdapter cards_adapter;
    private Timer timeToWipe;
    private TextView distance;
    public double RTT = 0;
    public Random rand = new Random();
    public int i = 0;
    public int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        cards_view = (ListView) findViewById(R.id.card_list);

        ArrayList<CreditCard> cards = Utils.getCards();
        cards_adapter = new CreditCardAdapter(this);
        cards_adapter.addAll(cards);
        cards_view.setAdapter(cards_adapter);
        distance = (TextView) findViewById(R.id.distance);

        timeToWipe = new Timer();

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                boolean num = getBluetooth();
                                timeToWipe.schedule(wipePrompt, 100000);

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (AlarmActivity.locked) {
            Button add_new = ((Button) findViewById(R.id.add_new));
            add_new.setBackgroundColor(Color.GRAY);
            add_new.setText("WALLET LOCKED -- Cards Blacklisted");
            AlarmActivity.locked = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
        return true;
    }

    public void addCard(View v) {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                if (!scanResult.isExpiryValid() || scanResult.cvv == null) return;
                cards_adapter.add(scanResult);
                cards_adapter.notifyDataSetInvalidated();
                cards_view.invalidate();
                Log.e("err", "hello?");
            }
        }
    }

    protected boolean getBluetooth(){
        return getCallingActivity().equals(500);   // get the bluetooth info
    }

    private TimerTask wipePrompt = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(CardActivity.this, AlarmActivity.class);
            startActivity(intent);
        }
    };
}

