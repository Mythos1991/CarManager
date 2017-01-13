package com.example.dc.carmanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    // SharedPreferences
    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;
    final String FUELKEY = "fuelkey";
    final String TEMPKEY = "tempkey";
    final String LEFTDOORKEY = "leftdoorkey";
    final String RIGHTDOORKEY = "rightdoorkey";
    final String BOTTOMDOORKEY = "bottomdoorkey";

    // Route
    private TextView fromTextView;
    private TextView toTextView;
    private ProgressBar routeProgressBar;
    private ImageButton pausePlayProgressImageButton;

    // Tank
    private SeekBar fuelSeekBar;
    private TextView fuelTextView;

    // Klimaanlage
    private SeekBar tempSeekBar;
    private TextView tempTextView;

    // Auto
    private ImageButton leftDoorImageButton;
    private ImageButton rightDoorImageButton;
    private ImageButton bottomDoorImageButton;
    private ImageButton startImageButton;

    // Buttons
    private ImageButton routeImageButton;
    private ImageButton panicImageButton;
    private ImageButton languageImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences
        prefs = this.getSharedPreferences("settings", MODE_PRIVATE);
        prefseditor = prefs.edit();

        // Route
        fromTextView = (TextView) findViewById(R.id.fromTextView);
        toTextView = (TextView) findViewById(R.id.toTextView);
        routeProgressBar = (ProgressBar) findViewById(R.id.routeProgressBar);
        pausePlayProgressImageButton = (ImageButton) findViewById(R.id.pausePlayProgressImageButton);

        // Tank
        final int FUEL_STEP = 1;
        final int FUEL_MAX = 100;
        final int FUEL_MIN = 0;
        fuelSeekBar = (SeekBar) findViewById(R.id.fuelSeekBar);

        fuelSeekBar.setMax((FUEL_MAX - FUEL_MIN) / FUEL_STEP);
        fuelTextView = (TextView) findViewById(R.id.fuelTextView);
        int savedFuel = prefs.getInt(FUELKEY, -1);
        fuelTextView.setText(Integer.toString(savedFuel) + "%");
        fuelSeekBar.setProgress(savedFuel);

        // Klimaanlage
        final int TEMP_STEP = 1;
        final int TEMP_MAX = 28;
        final int TEMP_MIN = 18;
        tempSeekBar = (SeekBar) findViewById(R.id.tempSeekBar);

        tempSeekBar.setMax((TEMP_MAX - TEMP_MIN) / TEMP_STEP);
        tempTextView = (TextView) findViewById(R.id.tempTextView);
        int savedTemp = prefs.getInt(TEMPKEY, -1);
        tempTextView.setText(Integer.toString(savedTemp) + "° C");
        tempSeekBar.setProgress((savedTemp % 18));

        // Auto / Doors
        leftDoorImageButton = (ImageButton) findViewById(R.id.doorLeft);
        rightDoorImageButton = (ImageButton) findViewById(R.id.doorRight);
        bottomDoorImageButton = (ImageButton) findViewById(R.id.doorBottom);
        startImageButton = (ImageButton) findViewById(R.id.startImageButton);

        Boolean savedLeftDoorLocked = prefs.getBoolean(LEFTDOORKEY, true);
        Boolean savedRightDoorLocked = prefs.getBoolean(RIGHTDOORKEY, true);
        Boolean savedBottomDoorLocked = prefs.getBoolean(BOTTOMDOORKEY, true);

        if (savedLeftDoorLocked) {
            leftDoorImageButton.setImageResource(R.drawable.lock_closed);
        }
        else {
            leftDoorImageButton.setImageResource(R.drawable.lock_open);
        }
        if (savedRightDoorLocked) {
            rightDoorImageButton.setImageResource(R.drawable.lock_closed);
        }
        else {
            rightDoorImageButton.setImageResource(R.drawable.lock_open);
        }
        if (savedBottomDoorLocked) {
            bottomDoorImageButton.setImageResource(R.drawable.lock_closed);
        }
        else {
            bottomDoorImageButton.setImageResource(R.drawable.lock_open);
        }

        // Buttons
        routeImageButton = (ImageButton) findViewById(R.id.routeImageButton);
        panicImageButton = (ImageButton) findViewById(R.id.panicImageButton);
        languageImageButton = (ImageButton) findViewById(R.id.languageImageButton);

        // Klimaanlage SeekbarChangeListener / Slider

        tempSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentTemp = TEMP_MIN + (progress * TEMP_STEP);
                String tempString = Integer.toString(currentTemp);
                tempTextView.setText(tempString + "° C");
                prefseditor.putInt(TEMPKEY, currentTemp);
                prefseditor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Tank SeekbarChangeListener / Slider
        fuelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentFuel = FUEL_MIN + (progress * FUEL_STEP);
                String fuelString = Integer.toString(currentFuel);
                fuelTextView.setText(fuelString + "%");
                prefseditor.putInt(FUELKEY, currentFuel);
                prefseditor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // Door OnClick Listener

    public void leftDoorClick(View v) {
        // get saved door states
        Boolean LeftDoorLocked = prefs.getBoolean(LEFTDOORKEY, true);

        // change door state by changing the image
        if (LeftDoorLocked) {
            leftDoorImageButton.setImageResource(R.drawable.lock_open);
        }
        else {
            leftDoorImageButton.setImageResource(R.drawable.lock_closed);
        }

        // save door state
        prefseditor.putBoolean(LEFTDOORKEY, (!LeftDoorLocked));
        prefseditor.commit();
    }

    public void rightDoorClick(View w) {
        // get saved door states
        Boolean RightDoorLocked = prefs.getBoolean(RIGHTDOORKEY, true);

        // change door state by changing the image
        if (RightDoorLocked) {
            rightDoorImageButton.setImageResource(R.drawable.lock_open);
        }
        else {
            rightDoorImageButton.setImageResource(R.drawable.lock_closed);
        }

        // save door state
        prefseditor.putBoolean(RIGHTDOORKEY, (!RightDoorLocked));
        prefseditor.commit();
    }

    public void bottomDoorClick(View x) {
        // get saved door states
        Boolean BottomDoorLocked = prefs.getBoolean(BOTTOMDOORKEY, true);

        // change door state by changing the image
        if (BottomDoorLocked) {
            bottomDoorImageButton.setImageResource(R.drawable.lock_open);
        }
        else {
            bottomDoorImageButton.setImageResource(R.drawable.lock_closed);
        }

        // save door state
        prefseditor.putBoolean(BOTTOMDOORKEY, (!BottomDoorLocked));
        prefseditor.commit();
    }

    // Thread to simulate progress while driving from A to B
    final Thread progressThread = new Thread(new Runnable() { public void run() {
        for (int i = 0; i <= 100; i++) {
            routeProgressBar.setProgress(i);

            // sleep to simulate progress
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }});

    public void StartClick(View y) {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        progressThread.start();
    }

    public void PausePlayClick(View z) {
        // TODO https://stackoverflow.com/questions/16221382/stop-thread-onclicklistener-java
    }

}