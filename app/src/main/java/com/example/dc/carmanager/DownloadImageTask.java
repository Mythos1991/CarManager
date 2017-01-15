package com.example.dc.carmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// TODO set default icon

class DownloadImageTask extends Thread implements Runnable {
    Bitmap mIcon_val = null;

    @Override
    public void run() {
        URL newurl = null;
        try {
            newurl = new URL("https://i.imgur.com/GDwf4R9.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpsURLConnection connection  = null;
        try {
            connection = (HttpsURLConnection) newurl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = null;
        try {
            is = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mIcon_val = BitmapFactory.decodeStream(is);

        // iconImageView.setImageBitmap(mIcon_val);
    }

    public Bitmap getBitmap () {
        return mIcon_val;
    }
}