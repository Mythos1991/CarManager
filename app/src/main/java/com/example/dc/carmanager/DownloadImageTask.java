package com.example.dc.carmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// TODO set default icon + default url
// TODO does it also work with http connections?

class DownloadImageTask extends Thread implements Runnable {
    Bitmap mIcon_val = null;
    String url = "https://www.fbi.h-da.de/fileadmin/personal/h.wiedling/daten/hoffart.jpg";

    public DownloadImageTask(String _url) {
        url = "https://www.fbi.h-da.de/fileadmin/personal/h.wiedling/daten/" + _url;
    }

    @Override
    public void run() {
        downloadImage();
    }

    private void downloadImage() {
        URL newurl = null;
        try {
            newurl = new URL(url);
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

    }

    public Bitmap getBitmap () {
        return mIcon_val;
    }
}