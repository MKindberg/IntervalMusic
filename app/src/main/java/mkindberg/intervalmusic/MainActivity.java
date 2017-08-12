package mkindberg.intervalmusic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer cdt;

    private boolean MODE_SETUP=false;
    private boolean MODE_TIMER=true;
    private boolean mode;

    private int work;
    private int rest;
    private int rounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.backgound).setBackgroundColor(Color.CYAN);
    }

    public void editWork(View v){
        EditText field = (EditText) findViewById(R.id.et_work);
        if(field.getText().length()==0)
            field.setText(0);
        if(((Button) v).getText().equals("+"))
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+1));
        else
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())-1));
    }

    public void editRest(View v){
        Log.i("Clicked", "Clicked");
        EditText field = (EditText) findViewById(R.id.et_rest);
        if(field.getText().length()==0)
            field.setText(0);
        if(((Button) v).getText().equals("+"))
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+1));
        else
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())-1));
    }

    public void editRounds(View v){
        EditText field = (EditText) findViewById(R.id.et_rounds);
        if(field.getText().length()==0)
            field.setText(0);
        if(((Button) v).getText().equals("+"))
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())+1));
        else
            field.setText(Integer.toString(Integer.parseInt(field.getText().toString())-1));
    }

    public void start(final View v){
        if(mode==MODE_SETUP) {
            getParams();
            toTimerView();
            startWork();
        }else{
            toSetupView();
            cdt.cancel();
        }
    }

    private void getParams(){
        work = Integer.parseInt(((EditText) findViewById(R.id.et_work)).getText().toString());
        rest = Integer.parseInt(((EditText) findViewById(R.id.et_rest)).getText().toString());
        rounds = Integer.parseInt(((EditText) findViewById(R.id.et_rounds)).getText().toString());
    }
    private void toTimerView() {
        ((Button) findViewById(R.id.btn_start)).setText("Stop");

        findViewById(R.id.layout_work).setVisibility(View.GONE);
        findViewById(R.id.layout_rest).setVisibility(View.GONE);
        findViewById(R.id.layout_rounds).setVisibility(View.GONE);

        findViewById(R.id.tv_seconds).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tv_seconds)).setText(Integer.toString(work));

        findViewById(R.id.backgound).setBackgroundColor(Color.GREEN);

        mode=MODE_TIMER;
    }
    private void toSetupView(){
        ((Button) findViewById(R.id.btn_start)).setText("Start");

        findViewById(R.id.layout_work).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_rest).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_rounds).setVisibility(View.VISIBLE);

        findViewById(R.id.tv_seconds).setVisibility(View.GONE);

        findViewById(R.id.backgound).setBackgroundColor(Color.CYAN);

        mode=MODE_SETUP;
    }

    private void startWork(){
        cdt = new CountDownTimer(work*1000, 950) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.tv_seconds)).setText(Long.toString((millisUntilFinished+500)/1000));
            }

            @Override
            public void onFinish() {
                beep(false);
                findViewById(R.id.backgound).setBackgroundColor(Color.RED);
                startRest();
            }
        };
        cdt.start();
    }
    private void startRest(){
        cdt = new CountDownTimer(rest*1000, 950) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.tv_seconds)).setText(Long.toString((millisUntilFinished+500)/1000));
            }

            @Override
            public void onFinish() {
                if(--rounds>0) {
                    beep(true);
                    findViewById(R.id.backgound).setBackgroundColor(Color.GREEN);
                    startWork();
                } else
                    toSetupView();
            }
        };
        cdt.start();
    }
    private void beep(boolean start){
        if(((CheckBox) findViewById(R.id.cb_beep)).isChecked()){
            ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_ALARM, 75);
            tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
            tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        }
        if(((CheckBox) findViewById(R.id.cb_music)).isChecked()){
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            Intent i = new Intent("com.android.music.musicservicecommand");
            if(start)
                i.putExtra("command" , "togglepause" );
            else
                i.putExtra("command" , "pause" );
            this.sendBroadcast(i);
        }
    }
}
