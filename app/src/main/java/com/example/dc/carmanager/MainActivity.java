package com.example.dc.carmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.example.dc.carmanager.Route.anzahlkey;
import static com.example.dc.carmanager.Route.textviewkey;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.dc.carmanager";
    Thread progressThread = null;

    // SharedPreferences
    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;
    final String FUELKEY = "fuelkey";
    final String TEMPKEY = "tempkey";
    final String LEFTDOORKEY = "leftdoorkey";
    final String RIGHTDOORKEY = "rightdoorkey";
    final String BOTTOMDOORKEY = "bottomdoorkey";
    final String LANGUAGEKEY = "languagekey";

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
    private ImageButton leftDoorImageButton;
    private ImageButton rightDoorImageButton;
    private ImageButton bottomDoorImageButton;
    private ImageButton startImageButton;
    Boolean driving;

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
        String lang = prefs.getString(LANGUAGEKEY, "de");

        // Language
        if (lang.equals("de")) {
            setLocale("de");
        }
        else if (lang.equals("en")){
            setLocale("en");
        }

        // Route
        fromTextView = (TextView) findViewById(R.id.fromTextView);
        toTextView = (TextView) findViewById(R.id.toTextView);
        routeProgressBar = (ProgressBar) findViewById(R.id.routeProgressBar);
        routeProgressBar.setProgress(0);

        // Tank
        final int FUEL_STEP = 1;
        final int FUEL_MAX = 100;
        final int FUEL_MIN = 0;
        fuelSeekBar = (SeekBar) findViewById(R.id.fuelSeekBar);

        fuelSeekBar.setMax((FUEL_MAX - FUEL_MIN) / FUEL_STEP);
        fuelTextView = (TextView) findViewById(R.id.fuelTextView);
        int savedFuel = prefs.getInt(FUELKEY, 100);
        fuelTextView.setText(Integer.toString(savedFuel) + "%");
        fuelSeekBar.setProgress(savedFuel);

        // Klimaanlage
        final int TEMP_STEP = 1;
        final int TEMP_MAX = 28;
        final int TEMP_MIN = 18;
        tempSeekBar = (SeekBar) findViewById(R.id.tempSeekBar);

        tempSeekBar.setMax((TEMP_MAX - TEMP_MIN) / TEMP_STEP);
        tempTextView = (TextView) findViewById(R.id.tempTextView);
        int savedTemp = prefs.getInt(TEMPKEY, 18);
        tempTextView.setText(Integer.toString(savedTemp) + "° C");
        tempSeekBar.setProgress((savedTemp % 18));

        // Auto / Doors
        leftDoorImageButton = (ImageButton) findViewById(R.id.doorLeft);
        rightDoorImageButton = (ImageButton) findViewById(R.id.doorRight);
        bottomDoorImageButton = (ImageButton) findViewById(R.id.doorBottom);
        startImageButton = (ImageButton) findViewById(R.id.startImageButton);
        driving = false;

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

        // Thread to simulate progress while driving from A to B
        progressThread = new Thread(new Runnable() { public void run() {
            for (int i = 0; i <= 100; i++) {
                // display progress
                routeProgressBar.setProgress(i);
                // sleep to simulate progress
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            driving = false;

            routeProgressBar.setProgress(0);

            startImageButton.getHandler().post(new Runnable() {
                public void run() {
                    startImageButton.setVisibility(View.VISIBLE);
                }
            });

            int amountFuel = prefs.getInt(FUELKEY, 90) - 10;
            if (amountFuel < 0) {
                amountFuel = 0;
            }
            prefseditor.putInt(FUELKEY, amountFuel);
            prefseditor.commit();

            fuelTextView.getHandler().post(new Runnable() {
                public void run() {
                    int amountFuel = prefs.getInt(FUELKEY, 90);
                    fuelTextView.setText(amountFuel + "%");
                }
            });

            fuelSeekBar.getHandler().post(new Runnable() {
                public void run() {
                    int amountFuel = prefs.getInt(FUELKEY, 90);
                    fuelSeekBar.setProgress(amountFuel);
                }
            });

            int anzahl = prefs.getInt(anzahlkey, 0);
            anzahl = anzahl - 1;
            if (anzahl < 0) {
                anzahl = 0;
            }
            prefseditor.putString(textviewkey[10], prefs.getString(textviewkey[0], ""));
            for (int i = 0; i < 8; i++) {
                prefseditor.putString(textviewkey[i], prefs.getString(textviewkey[i+1], ""));
            }
            prefseditor.putString(textviewkey[9], "");

            prefseditor.putInt("anzahlkey", anzahl);
            prefseditor.commit();

            fromTextView.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    fromTextView.setText(getApplicationContext().getString(R.string.realFrom) + ": " + prefs.getString(textviewkey[10], ""));
                }
            });

            toTextView.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    toTextView.setText(getApplicationContext().getString(R.string.realTo) + ": " + prefs.getString(textviewkey[0], " "));
                }
            });

        }});
    }

    @Override
    public void onResume() {
        super.onResume();

        fromTextView.setText(getApplicationContext().getString(R.string.realFrom) + ": " + prefs.getString(textviewkey[10], ""));
        toTextView.setText(getApplicationContext().getString(R.string.realTo) + ": " + prefs.getString(textviewkey[0], " "));

        // Thread to simulate progress while driving from A to B
        progressThread = new Thread(new Runnable() { public void run() {
            for (int i = 0; i <= 100; i++) {
                // display progress
                routeProgressBar.setProgress(i);
                // sleep to simulate progress
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            driving = false;

            routeProgressBar.setProgress(0);

            startImageButton.getHandler().post(new Runnable() {
                public void run() {
                    startImageButton.setVisibility(View.VISIBLE);
                }
            });

            int amountFuel = prefs.getInt(FUELKEY, 90) - 10;
            if (amountFuel < 0) {
                amountFuel = 0;
            }
            prefseditor.putInt(FUELKEY, amountFuel);
            prefseditor.commit();

            fuelTextView.getHandler().post(new Runnable() {
                public void run() {
                    int amountFuel = prefs.getInt(FUELKEY, 90);
                    fuelTextView.setText(amountFuel + "%");
                }
            });

            fuelSeekBar.getHandler().post(new Runnable() {
                public void run() {
                    int amountFuel = prefs.getInt(FUELKEY, 90);
                    fuelSeekBar.setProgress(amountFuel);
                }
            });

            int anzahl = prefs.getInt(anzahlkey, 0);
            anzahl = anzahl - 1;
            if (anzahl < 0) {
                anzahl = 0;
            }
            prefseditor.putString(textviewkey[10], prefs.getString(textviewkey[0], ""));
            for (int i = 0; i < 8; i++) {
                prefseditor.putString(textviewkey[i], prefs.getString(textviewkey[i+1], ""));
            }
            prefseditor.putString(textviewkey[9], "");

            prefseditor.putInt("anzahlkey", anzahl);
            prefseditor.commit();

            fromTextView.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    fromTextView.setText(getApplicationContext().getString(R.string.realFrom) + ": " + prefs.getString(textviewkey[10], ""));
                }
            });

            toTextView.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    toTextView.setText(getApplicationContext().getString(R.string.realTo) + ": " + prefs.getString(textviewkey[0], " "));
                }
            });

        }});
    }

    // Door OnClick Listener

    public void leftDoorClick(View v) {
        if (!driving) {
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
    }

    public void rightDoorClick(View v) {
        if (!driving) {
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
    }

    public void bottomDoorClick(View v) {
        if (!driving) {
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
    }

    public void panicClick(View v) {
        bottomDoorImageButton.setImageResource(R.drawable.lock_closed);
        rightDoorImageButton.setImageResource(R.drawable.lock_closed);
        leftDoorImageButton.setImageResource(R.drawable.lock_closed);

        prefseditor.putBoolean(LEFTDOORKEY, true);
        prefseditor.putBoolean(RIGHTDOORKEY, true);
        prefseditor.putBoolean(BOTTOMDOORKEY, true);
        prefseditor.commit();
    }

    public void startRouteActivity(View v) {
        Intent intent = new Intent(this, Route.class);
        String message = "testtesttest";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void changeLanguage(View v) {
        Intent intent = new Intent(this, Settings.class);
        String message = "testtesttest";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void StartClick(View v) {
        if ((!driving)) {
            Boolean BottomDoorLocked = prefs.getBoolean(BOTTOMDOORKEY, true);
            Boolean LeftDoorLocked = prefs.getBoolean(LEFTDOORKEY, true);
            Boolean RightDoorLocked = prefs.getBoolean(RIGHTDOORKEY, true);
            int amountFuel = prefs.getInt(FUELKEY, -1);
            int anzahl = prefs.getInt(anzahlkey, 0);

            if (BottomDoorLocked && LeftDoorLocked && RightDoorLocked && (amountFuel > 0) && (anzahl >= 1)) {
                driving = true;
                startImageButton.setVisibility(View.INVISIBLE);

                Context context = getApplicationContext();
                String message = context.getString(R.string.startsuccessful);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();

                fromTextView.setText(getApplicationContext().getString(R.string.realFrom) + ": " + prefs.getString(textviewkey[10], ""));
                toTextView.setText(getApplicationContext().getString(R.string.realTo) + ": " + prefs.getString(textviewkey[0], " "));

                progressThread.start();
            }
            else {
                Context context = getApplicationContext();
                String message = context.getString(R.string.starterror);
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {

        }
    }

    public void setLocale(String lang) {
        Context context = getApplicationContext();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }
}