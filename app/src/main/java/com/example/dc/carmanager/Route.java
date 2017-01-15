package com.example.dc.carmanager;

import android.app.Activity;
import org.json.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.dc.carmanager.MainActivity.EXTRA_MESSAGE;

public class Route extends Activity implements View.OnClickListener {
    SharedPreferences prefs;
    SharedPreferences.Editor prefseditor;

    private int anzahl=-1;
    private boolean startort=false;

    final private String[] textviewkey={"textview0key","textview1key",
            "textview2key","textview3key", "textview4key",
            "textview5key", "textview6key", "textview7key",
            "textview8key", "textview9key","startkey"};
    final private String anzahlkey="anzahlkey";
    private String startbuttonstr = "Start Eingeben";


    private String key = "key";
    public static TextView [] tv_a = new TextView[10];
    public static TextView [] tv_b = new TextView[11];
    public static Button [] del = new Button[10];
    Button add, save;
    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        prefs = this.getSharedPreferences("settings", MODE_PRIVATE);
        prefseditor = prefs.edit();

        anzahl = prefs.getInt(anzahlkey, 0);

        add=(Button) findViewById(R.id.button_add);

        tv_a[0] = (TextView) findViewById(R.id.textView1);
        tv_a[1] = (TextView) findViewById(R.id.textView2);
        tv_a[2] = (TextView) findViewById(R.id.textView3);
        tv_a[3] = (TextView) findViewById(R.id.textView4);
        tv_a[4] = (TextView) findViewById(R.id.textView5);
        tv_a[5] = (TextView) findViewById(R.id.textView6);
        tv_a[6] = (TextView) findViewById(R.id.textView7);
        tv_a[7] = (TextView) findViewById(R.id.textView8);
        tv_a[8] = (TextView) findViewById(R.id.textView9);
        tv_a[9] = (TextView) findViewById(R.id.textView10);


        tv_b[0] = (TextView) findViewById(R.id.textView20);
        tv_b[1] = (TextView) findViewById(R.id.textView21);
        tv_b[2] = (TextView) findViewById(R.id.textView22);
        tv_b[3] = (TextView) findViewById(R.id.textView23);
        tv_b[4] = (TextView) findViewById(R.id.textView24);
        tv_b[5] = (TextView) findViewById(R.id.textView25);
        tv_b[6] = (TextView) findViewById(R.id.textView26);
        tv_b[7] = (TextView) findViewById(R.id.textView27);
        tv_b[8] = (TextView) findViewById(R.id.textView28);
        tv_b[9] = (TextView) findViewById(R.id.textView29);
        tv_b[10] = (TextView) findViewById(R.id.StartTextview);


        del[0] = (Button) findViewById(R.id.button1);
        del[1] = (Button) findViewById(R.id.button2);
        del[2] = (Button) findViewById(R.id.button3);
        del[3] = (Button) findViewById(R.id.button4);
        del[4] = (Button) findViewById(R.id.button5);
        del[5] = (Button) findViewById(R.id.button6);
        del[6] = (Button) findViewById(R.id.button7);
        del[7] = (Button) findViewById(R.id.button8);
        del[8] = (Button) findViewById(R.id.button9);
        del[9] = (Button) findViewById(R.id.button10);




        for(int i=0;i<11;i++){
            tv_b[i].setText(prefs.getString(textviewkey[i], " "));
            prefseditor.putString(textviewkey[i], tv_b[i].getText().toString());
            prefseditor.commit();
        }

        //lv= (ListView) findViewById(R.id.ListView1);

        for(int i=0;i<10;i++){
            del[i].setOnClickListener(this);
        }

        for(int i=10; i>anzahl;i--){
            del[i-1].setVisibility(View.INVISIBLE);
            tv_b[i-1].setVisibility(View.INVISIBLE);
            tv_a[i-1].setVisibility(View.INVISIBLE);
        }


    }

    public void add (View v) {
        if (anzahl<10){

            anzahl=anzahl+1;

            Intent intent = new Intent(this, POI.class);
            intent.putExtra( EXTRA_MESSAGE , (anzahl-1));

            prefseditor.putInt(anzahlkey, anzahl);
            prefseditor.commit();
            startActivity(intent);
        }

    }

    private void delete(int pos){

        int diff=anzahl-pos-1;
        if(diff>=0){
            while(diff>0){
                tv_b[pos].setText(tv_b[pos+1].getText());
                prefseditor.putString(textviewkey[pos], tv_b[pos].getText().toString());
                diff--;
                pos++;
            }
            tv_b[pos].setText(" ");
            prefseditor.putString(textviewkey[pos], tv_b[pos].getText().toString());
            prefseditor.commit();
            tv_a[pos].setVisibility(View.INVISIBLE);
            tv_b[pos].setVisibility(View.INVISIBLE);
            del[pos].setVisibility(View.INVISIBLE);
            anzahl--;
            prefseditor.putInt(anzahlkey, anzahl);
            prefseditor.commit();
        }

    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button1:
                delete(0);
                break;

            case R.id.button2:
                delete(1);
                break;

            case R.id.button3:
                delete(2);
                break;
            case R.id.button4:
                delete(3);
                break;

            case R.id.button5:
                delete(4);
                break;

            case R.id.button6:
                delete(5);
                break;

            case R.id.button7:
                delete(6);
                break;

            case R.id.button8:
                delete(7);
                break;

            case R.id.button9:
                delete(8);
                break;

            case R.id.button10:
                delete(9);
                break;

            default:
                break;
        }
    }




    public void save(View view) {
        Bundle b = new Bundle();
        String str[] = new String[11];
        str[0] = tv_b[10].getText().toString();
        for(int i=1;i<str.length; i++){
            str[i]=tv_b[i-1].getText().toString();
        }
        b.putStringArray(EXTRA_MESSAGE , str);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }


}
