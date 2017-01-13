package com.example.dc.carmanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    // Tank
    private SeekBar fuelSeekBar;
    private TextView fuelTextView;

    // Klimaanlage
    private SeekBar tempSeekBar;
    private TextView tempTextView;

    // Auto
    private ImageButton doorLeftImageButton;
    private ImageButton doorRightImageButton;
    private ImageButton doorBottomImageButton;
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

        // Auto
        doorLeftImageButton = (ImageButton) findViewById(R.id.doorLeft);
        doorRightImageButton = (ImageButton) findViewById(R.id.doorRight);
        doorBottomImageButton = (ImageButton) findViewById(R.id.doorBottom);
        startImageButton = (ImageButton) findViewById(R.id.startImageButton);

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

    public void leftDoorClick() {

    }

    public void rightDoorClick() {

    }

    public void bottomDoorClick() {
        
    }

}