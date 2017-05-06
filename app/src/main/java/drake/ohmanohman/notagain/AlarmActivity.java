package drake.ohmanohman.notagain;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fima.glowpadview.GlowPadView;

public class AlarmActivity extends Activity implements GlowPadView.OnTriggerListener{

    private GlowPadView swiper;
    private FraudExecuter exec;
    public static boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        swiper = (GlowPadView) findViewById(R.id.glow_pad_view);
        swiper.setOnTriggerListener(this);
        exec = new FraudExecuter(this);
    }

    @Override
    public void onTrigger(View v, int target) {
        final int resId = swiper.getResourceIdForTarget(target);
        switch (resId) {
            case R.drawable.yesnormal:
                exec.executeFraud();
                locked = true;
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onGrabbed(View v, int handle) {}
    @Override
    public void onReleased(View v, int handle) {   }
    @Override
    public void onGrabbedStateChange(View v, int handle) { }
    @Override
    public void onFinishFinalAnimation() {   }
}
