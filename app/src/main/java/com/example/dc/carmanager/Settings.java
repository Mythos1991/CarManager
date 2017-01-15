package com.example.dc.carmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;

public class Settings extends AppCompatActivity {
    RadioButton germanRadioButton;
    RadioButton englishRadioButton;

    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;
    final String LANGUAGEKEY = "languagekey";
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        germanRadioButton = (RadioButton) findViewById(R.id.germanRadioButton);
        englishRadioButton = (RadioButton) findViewById(R.id.englishRadioButton);

        prefs = this.getSharedPreferences("settings", MODE_PRIVATE);
        prefseditor = prefs.edit();
        lang = prefs.getString(LANGUAGEKEY, "de");
        if (lang.equals("de")) {
            germanRadioButton.setChecked(true);
            englishRadioButton.setChecked(false);
        }
        else if (lang.equals("en")){
            germanRadioButton.setChecked(false);
            englishRadioButton.setChecked(true);
        }
    }

    public void onGermanClick (View v) {
        englishRadioButton.setChecked(false);
        prefseditor.putString(LANGUAGEKEY, "de");
        prefseditor.commit();
        setLocale("de");
        Toast.makeText(Settings.this, "Sprache erfolgreich geändert!", Toast.LENGTH_SHORT).show();
    }

    public void onEnglishClick (View v) {
        germanRadioButton.setChecked(false);
        prefseditor.putString(LANGUAGEKEY, "en");
        prefseditor.commit();
        setLocale("en");
        Toast.makeText(Settings.this, "Language successfully changed!", Toast.LENGTH_SHORT).show();
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
