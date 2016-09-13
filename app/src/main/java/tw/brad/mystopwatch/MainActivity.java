package tw.brad.mystopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView clock;
    private Button btnLeft, btnRight;
    private boolean isRunning;
    private int counter;
    private Timer timer;
    private UIHandler handler;
    private CountTask countTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clock = (TextView)findViewById(R.id.clock);
        btnLeft = (Button)findViewById(R.id.btnLeft);
        btnRight = (Button)findViewById(R.id.btnRight);

        timer = new Timer();
        handler = new UIHandler();
    }

    @Override
    public void finish() {
        timer.purge();
        timer.cancel();
        timer = null;

        super.finish();
    }

    // Reset / Lap
    public void doLeft(View v){
        if (isRunning){
            doLap();
        }else{
            doReset();
        }
    }
    // Start / Stop
    public void doRight(View v){
        isRunning = !isRunning;
        btnRight.setText(isRunning?"Stop":"Start");
        btnLeft.setText(isRunning?"Lap":"Reset");
        if (isRunning){
            doStart();
        }else{
            doStop();
        }
    }

    private void doStart(){
        countTask = new CountTask();
        timer.schedule(countTask, 0, 10);
    }
    private void doStop(){
        if (countTask != null) {
            countTask.cancel();
            countTask = null;
        }
        counter = 0;
        handler.sendEmptyMessage(0);
    }
    private void doLap(){

    }
    private void doReset(){

    }

    private class CountTask extends TimerTask {
        @Override
        public void run() {
            counter++;
            handler.sendEmptyMessage(0);
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            clock.setText("" + counter);
        }
    }

}
