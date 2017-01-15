package com.example.dc.carmanager;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

// TODO onWhateverClick: variable, wenn button einmal geklickt, code nicht nochmal ausführen
// TODO onAllClick: anderen 3 Buttons resetten, variable zurücksetzen ^
// TODO onAllClick: Array aus den 3 anderen Arrays machen

public class POI extends AppCompatActivity {
    ArrayList<JSONObject> allPOIs, pubPOIs, fuelPOIs, amenityPOIs;

    RadioButton allRadioButton;
    RadioButton pubRadioButton;
    RadioButton fuelRadioButton;
    RadioButton amenityRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);

        allRadioButton = (RadioButton) findViewById(R.id.allRadioButton);
        pubRadioButton = (RadioButton) findViewById(R.id.pubRadioButton);
        fuelRadioButton = (RadioButton) findViewById(R.id.fuelRadioButton);
        amenityRadioButton = (RadioButton) findViewById(R.id.amenityRadioButton);

        allPOIs = new ArrayList<>();
        pubPOIs = new ArrayList<>();
        fuelPOIs = new ArrayList<>();
        amenityPOIs = new ArrayList<>();

        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        JSONObject obj3 = new JSONObject();
        JSONObject obj4 = new JSONObject();

        try {
            obj1.put("NAME", "Anton");
            obj2.put("NAME", "Beate");
            obj3.put("NAME", "Can");
            obj4.put("NAME", "Dorothy");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pubPOIs.add(obj1);
        pubPOIs.add(obj2);
        fuelPOIs.add(obj3);
        amenityPOIs.add(obj4);

        for (int i = 0; i < pubPOIs.size(); i++) {
            allPOIs.add(pubPOIs.get(i));
        }
        for (int i = 0; i < fuelPOIs.size(); i++) {
            allPOIs.add(fuelPOIs.get(i));
        }
        for (int i = 0; i < amenityPOIs.size(); i++) {
            allPOIs.add(amenityPOIs.get(i));
        }

    }

    public void onAllClick (View v) {
        fuelRadioButton.setChecked(false);
        pubRadioButton.setChecked(false);
        amenityRadioButton.setChecked(false);

        ListAdapter myAdapter = new CustomAdapter(this, allPOIs);
        ListView myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String toastText = String.valueOf(parent.getItemAtPosition(position)).toString();
                        Toast.makeText(POI.this, toastText, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void onPubOnlyClick (View v) {
        fuelRadioButton.setChecked(false);
        allRadioButton.setChecked(false);
        amenityRadioButton.setChecked(false);

        ListAdapter myAdapter = new CustomAdapter(this, pubPOIs);
        ListView myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String toastText = String.valueOf(parent.getItemAtPosition(position)).toString();
                        Toast.makeText(POI.this, toastText, Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    public void onFuelOnlyClick (View v) {
        allRadioButton.setChecked(false);
        pubRadioButton.setChecked(false);
        amenityRadioButton.setChecked(false);

        ListAdapter myAdapter = new CustomAdapter(this, fuelPOIs);
        ListView myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String toastText = String.valueOf(parent.getItemAtPosition(position)).toString();
                        Toast.makeText(POI.this, toastText, Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    public void onAmenityOnlyClick (View v) {
        fuelRadioButton.setChecked(false);
        pubRadioButton.setChecked(false);
        allRadioButton.setChecked(false);

        ListAdapter myAdapter = new CustomAdapter(this, amenityPOIs);
        ListView myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String toastText = String.valueOf(parent.getItemAtPosition(position)).toString();
                        Toast.makeText(POI.this, toastText, Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

}
