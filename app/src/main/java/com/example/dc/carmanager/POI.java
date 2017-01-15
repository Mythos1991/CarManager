package com.example.dc.carmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.example.dc.carmanager.Route.tv_a;
import static com.example.dc.carmanager.Route.tv_b;
import static com.example.dc.carmanager.Route.del;

import static com.example.dc.carmanager.MainActivity.EXTRA_MESSAGE;

// TODO onItemCLickListener add to Route activity

public class POI extends AppCompatActivity {
    // SharedPreferences
    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;

    ArrayList<JSONObject> allPOIs, pubPOIs, fuelPOIs, amenityPOIs;

    RadioButton allRadioButton;
    RadioButton pubRadioButton;
    RadioButton fuelRadioButton;
    RadioButton amenityRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);

        prefs = this.getSharedPreferences("settings", MODE_PRIVATE);
        prefseditor = prefs.edit();

        allRadioButton = (RadioButton) findViewById(R.id.allRadioButton);
        pubRadioButton = (RadioButton) findViewById(R.id.pubRadioButton);
        fuelRadioButton = (RadioButton) findViewById(R.id.fuelRadioButton);
        amenityRadioButton = (RadioButton) findViewById(R.id.amenityRadioButton);

        init();
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
                        Intent intent = getIntent();
                        int message = intent.getIntExtra(EXTRA_MESSAGE, -2);

                        if (message == -1) {
                            try {
                                tv_b[10].setText(allPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            tv_a[message].setVisibility(View.VISIBLE);
                            tv_b[message].setVisibility(View.VISIBLE);
                            del[message].setVisibility(View.VISIBLE);
                            try {
                                tv_b[message].setText(allPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        message = message + 1;
                        prefseditor.putInt("anzahlkey", message);
                        prefseditor.commit();

                        finish();
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
                        Intent intent = getIntent();
                        int message = intent.getIntExtra(EXTRA_MESSAGE, -2);

                        if (message == -1) {
                            try {
                                tv_b[10].setText(pubPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            tv_a[message].setVisibility(View.VISIBLE);
                            tv_b[message].setVisibility(View.VISIBLE);
                            del[message].setVisibility(View.VISIBLE);
                            try {
                                tv_b[message].setText(pubPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        message = message + 1;
                        prefseditor.putInt("anzahlkey", message);
                        prefseditor.commit();

                        finish();
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
                        Intent intent = getIntent();
                        int message = intent.getIntExtra(EXTRA_MESSAGE, -2);

                        if (message == -1) {
                            try {
                                tv_b[10].setText(fuelPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            tv_a[message].setVisibility(View.VISIBLE);
                            tv_b[message].setVisibility(View.VISIBLE);
                            del[message].setVisibility(View.VISIBLE);
                            try {
                                tv_b[message].setText(fuelPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        message = message + 1;
                        prefseditor.putInt("anzahlkey", message);
                        prefseditor.commit();

                        finish();
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
                        Intent intent = getIntent();
                        int message = intent.getIntExtra(EXTRA_MESSAGE, -2);

                        if (message == -1) {
                            try {
                                tv_b[10].setText(amenityPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            tv_a[message].setVisibility(View.VISIBLE);
                            tv_b[message].setVisibility(View.VISIBLE);
                            del[message].setVisibility(View.VISIBLE);
                            try {
                                tv_b[message].setText(amenityPOIs.get(position).get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        message = message + 1;
                        prefseditor.putInt("anzahlkey", message);
                        prefseditor.commit();

                        finish();
                    }
                }
        );

    }




    private void init() {
        allPOIs = new ArrayList<>();
        pubPOIs = new ArrayList<>();
        fuelPOIs = new ArrayList<>();
        amenityPOIs = new ArrayList<>();

        JSONObject obj0 = new JSONObject();
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        JSONObject obj3 = new JSONObject();
        JSONObject obj4 = new JSONObject();
        JSONObject obj5 = new JSONObject();
        JSONObject obj6 = new JSONObject();
        JSONObject obj7 = new JSONObject();
        JSONObject obj8 = new JSONObject();
        JSONObject obj9 = new JSONObject();
        JSONObject obj10 = new JSONObject();
        JSONObject obj11 = new JSONObject();
        JSONObject obj12 = new JSONObject();
        JSONObject obj13 = new JSONObject();
        JSONObject obj14 = new JSONObject();
        JSONObject obj15 = new JSONObject();
        JSONObject obj16 = new JSONObject();
        JSONObject obj17 = new JSONObject();
        JSONObject obj18 = new JSONObject();
        JSONObject obj19 = new JSONObject();
        JSONObject obj20 = new JSONObject();
        JSONObject obj21 = new JSONObject();
        JSONObject obj22 = new JSONObject();
        JSONObject obj23 = new JSONObject();
        JSONObject obj24 = new JSONObject();
        JSONObject obj25 = new JSONObject();
        JSONObject obj26 = new JSONObject();
        JSONObject obj27 = new JSONObject();
        JSONObject obj28 = new JSONObject();
        JSONObject obj29 = new JSONObject();

        try {
            obj0.put("type", "fuel");
            obj0.put("name", "Esso");
            obj0.put("address", "Frankfurter Straße 65");
            obj0.put("lat", "49.8848387");
            obj0.put("lon", "8.6520691");

            obj1.put("type", "amenity");
            obj1.put("name", "HoffART Theater");
            obj1.put("address", "Bennelbächerweg");
            obj1.put("lat", "49.8784609");
            obj1.put("lon", "8.6593199");
            obj1.put("icon", "hoffart.jpg");

            obj2.put("type", "fuel");
            obj2.put("name", "Total");
            obj2.put("address", "Siemensstraße 2");
            obj2.put("lat", "49.8959045");
            obj2.put("lon", "8.6809781");

            obj3.put("type", "pub");
            obj3.put("name", "Cluster");
            obj3.put("address", "Wilhelm-Leuschner-Straße 48");
            obj3.put("lat", "49.8801245");
            obj3.put("lon", "8.6453214");
            obj3.put("capacity", "130");

            obj4.put("type", "pub");
            obj4.put("name", "Pillhuhn");
            obj4.put("address", "Riegerplatz 7");
            obj4.put("lat", "49.8809091");
            obj4.put("lon", "8.6612666");
            obj4.put("capacity", "65");

            obj5.put("type", "fuel");
            obj5.put("name", "Aral Tankstelle");
            obj5.put("address", "Rüdesheimer Straße 114");
            obj5.put("lat", "49.8540121");
            obj5.put("lon", "8.6419863");

            obj6.put("type", "fuel");
            obj6.put("name", "Total");
            obj6.put("address", "Heidelberger Straße 55-59");
            obj6.put("lat", "49.8614199");
            obj6.put("lon", "8.6468131");

            obj7.put("type", "fuel");
            obj7.put("name", "Firma Karaahmetaglu");
            obj7.put("address", "Pallaswiesenviertel Pallaswiesenstraße");
            obj7.put("lat", "49.8847555");
            obj7.put("lon", "8.6311635");

            obj8.put("type", "fuel");
            obj8.put("name", "Shell");
            obj8.put("address", "Pallaswiesenstraße 85");
            obj8.put("lat", "49.8825735");
            obj8.put("lon", "8.6429677");

            obj9.put("type", "fuel");
            obj9.put("name", "Jet");
            obj9.put("address", "Johannesviertel Kasinostraße");
            obj9.put("lat", "49.8796515");
            obj9.put("lon", "8.6443997");

            obj10.put("type", "fuel");
            obj10.put("name", "Shell");
            obj10.put("address", "Verlegerviertel Groß-Gerauer Weg");
            obj10.put("lat", "49.8610075");
            obj10.put("lon", "8.6438008");

            obj11.put("type", "fuel");
            obj11.put("name", "Bio World");
            obj11.put("address", "Alt-Bessungen Heidelberger Straße");
            obj11.put("lat", "49.860629");
            obj11.put("lon", "8.6467905");

            obj12.put("type", "amenity");
            obj12.put("name", "Centralstation");
            obj12.put("address", "Darmstadt-Mitte Carree");
            obj12.put("lat", "49.87176535");
            obj12.put("lon", "8.65256409386837");
            obj12.put("icon", "centralstation.jpg");

            obj13.put("type", "fuel");
            obj13.put("name", "Shell");
            obj13.put("address", "Auf der Hardt Am Nordbahnhof");
            obj13.put("lat", "49.8907637");
            obj13.put("lon", "8.65370579283444");

            obj14.put("type", "amenity");
            obj14.put("name", "Waldspirale");
            obj14.put("address", "Waldspirale 8");
            obj14.put("lat", "49.885555555556");
            obj14.put("lon", "8.6558333333333");
            obj14.put("icon", "waldspirale.jpg");

            obj15.put("type", "pub");
            obj15.put("name", "Zum Eckchen");
            obj15.put("address", "Johannesviertel Kasinostraße");
            obj15.put("lat", "49.8749139");
            obj15.put("lon", "8.6435668");
            obj15.put("capacity", "40");

            obj16.put("type", "amenity");
            obj16.put("name", "Künstlerkolonie");
            obj16.put("address", "Mathildenhöhe");
            obj16.put("lat", "49.879707");
            obj16.put("lon", "8.65334");
            obj16.put("icon", "mathildenhoehe.jpg");

            obj17.put("type", "amenity");
            obj17.put("name", "West Side Theatre");
            obj17.put("address", "Landwehrstraße 58");
            obj17.put("lat", "49.8782889");
            obj17.put("lon", "8.63950418824466");
            obj17.put("icon", "westsidetheatre.jpeg");

            obj29.put("type", "pub");
            obj29.put("name", "Unikum");
            obj29.put("address", "Mollerstadt Saalbaustraße");
            obj29.put("lat", "49.8714409");
            obj29.put("lon", "8.647085");
            obj29.put("capacity", "90");

            obj28.put("type", "pub");
            obj28.put("name", "Cafe Tornado");
            obj28.put("address", "Mollerstadt Saalbaustraße");
            obj28.put("lat", "49.8715443");
            obj28.put("lon", "8.6470329");
            obj28.put("capacity", "55");

            obj27.put("type", "pub");
            obj27.put("name", "Bangertseck");
            obj27.put("address", "Barkhausstraße 2");
            obj27.put("lat", "49.8824086");
            obj27.put("lon", "8.6594886");
            obj27.put("capacity", "45");

            obj26.put("type", "pub");
            obj26.put("name", "Parliament of Rock");
            obj26.put("address", "Mauerstraße 20");
            obj26.put("lat", "49.87694");
            obj26.put("lon", "8.6594569");
            obj26.put("capacity", "60");

            obj25.put("type", "pub");
            obj25.put("name", "Grauer Bock");
            obj25.put("address", "Kasinostraße 71");
            obj25.put("lat", "49.8790803");
            obj25.put("lon", "8.6441621");
            obj25.put("capacity", "65");

            obj24.put("type", "pub");
            obj24.put("name", "La Bodega");
            obj24.put("address", "Kahlertstraße 34");
            obj24.put("lat", "49.8798651");
            obj24.put("lon", "8.6454158");
            obj24.put("capacity", "40");

            obj23.put("type", "pub");
            obj23.put("name", "Beat Corner");
            obj23.put("address", "Schulstraße 18");
            obj23.put("lat", "49.8707072");
            obj23.put("lon", "8.6565291");
            obj23.put("capacity", "70");

            obj22.put("type", "amenity");
            obj22.put("name", "Staatstheater");
            obj22.put("address", "Georg-Büchner-Platz 1");
            obj22.put("lat", "49.868501");
            obj22.put("lon", "8.649341");
            obj22.put("icon", "staatstheater.gif");

            obj21.put("type", "amenity");
            obj21.put("name", "Kikeriki Theater");
            obj21.put("address", "Heidelberger Straße 131");
            obj21.put("lat", "49.8552092");
            obj21.put("lon", "8.6464438");
            obj21.put("icon", "kikeriki.gif");

            obj20.put("type", "amenity");
            obj20.put("name", "halb Neun Theater");
            obj20.put("address", "Sandstraße 32");
            obj20.put("lat", "49.8674069");
            obj20.put("lon", "8.6473876");
            obj20.put("icon", "halbneuntheater.jpg");

            obj19.put("type", "amenity");
            obj19.put("name", "Jagdhofkeller");
            obj19.put("address", "Alt-Bessungen Bessunger Straße");
            obj19.put("lat", "49.8581624");
            obj19.put("lon", "8.6486246");
            obj19.put("icon", "jagdhofkeller.jpg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        allPOIs.add(obj0);
        allPOIs.add(obj1);
        allPOIs.add(obj2);
        allPOIs.add(obj3);
        allPOIs.add(obj4);
        allPOIs.add(obj5);
        allPOIs.add(obj6);
        allPOIs.add(obj7);
        allPOIs.add(obj8);
        allPOIs.add(obj9);
        allPOIs.add(obj10);
        allPOIs.add(obj11);
        allPOIs.add(obj12);
        allPOIs.add(obj13);
        allPOIs.add(obj14);
        allPOIs.add(obj15);
        allPOIs.add(obj16);
        allPOIs.add(obj17);
        allPOIs.add(obj19);
        allPOIs.add(obj20);
        allPOIs.add(obj21);
        allPOIs.add(obj22);
        allPOIs.add(obj23);
        allPOIs.add(obj24);
        allPOIs.add(obj25);
        allPOIs.add(obj26);
        allPOIs.add(obj27);
        allPOIs.add(obj28);
        allPOIs.add(obj29);

        for (int i = 0; i < allPOIs.size(); i++) {
            String type = null;
            try {
                type = allPOIs.get(i).getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            switch (type) {
                case "fuel":
                    fuelPOIs.add(allPOIs.get(i));
                    break;
                case "amenity":
                    amenityPOIs.add(allPOIs.get(i));
                    break;
                case "pub":
                    pubPOIs.add(allPOIs.get(i));
                    break;
            }
        }
    }

}
