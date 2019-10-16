package com.example.erikh.reach.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.erikh.reach.R;


//https://www.youtube.com/watch?v=RLnb4vVkftc
//https://stackoverflow.com/questions/3320115/android-onclicklistener-identify-a-button/3320148#3320148
public class Counter extends Activity  implements View.OnClickListener {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    Button startButton;
    Button stopButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        chronometer = findViewById(R.id.chronometer);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        resetButton = (Button) findViewById(R.id.resetButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                startChronometer();
                Log.d("tag?", "onClick: start");
                break;
            case R.id.stopButton:
                stopChronometer();
                Log.d("tag?", "onClick: stop");
                break;
            case R.id.resetButton:
                resetChronometer();
                Log.d("tag?", "onClick: reset");
                break;
        }
    }

    private void startChronometer() {
        Log.d("tag?", "onClick: startmethod");
        if(!running){
            Log.d("tag?", "onClick: startrunning");
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    private void stopChronometer() {
        Log.d("tag?", "onClick: !startmethod");
        if(running){
            Log.d("tag?", "onClick: !startrunning");
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    private void resetChronometer() {
        Log.d("tag?", "onClick: reset");
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

}
