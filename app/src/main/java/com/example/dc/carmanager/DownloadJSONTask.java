package com.example.dc.carmanager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class DownloadJSONTask extends Thread implements Runnable {
    String url = "https://www.fbi.h-da.de/fileadmin/personal/h.wiedling/daten/poi";
    InputStream is;

    public DownloadJSONTask() {

    }

    public DownloadJSONTask(String _url) {
        url = _url;
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

        is = null;
        try {
            is = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public InputStream getJSONInputStream () {
        return is;
    }
}