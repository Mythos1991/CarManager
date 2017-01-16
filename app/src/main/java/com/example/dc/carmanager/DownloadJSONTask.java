package com.example.dc.carmanager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class DownloadJSONTask extends Thread implements Runnable {
    String sourceUrl = "https://www.fbi.h-da.de/fileadmin/personal/h.wiedling/daten/poi";
    String jsonstring = "";

    public DownloadJSONTask() {

    }

    public DownloadJSONTask(String _url) {
        sourceUrl = _url;
    }

    public String getJSONasString () {
        return jsonstring;
    }

    @Override
    public void run() {
        downloadJSON();
    }

    private void downloadJSON() {
        StringBuilder response = null;
        try {
            URL url = new URL(sourceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader r = new BufferedReader(
                    new InputStreamReader( con.getInputStream() )
            );
            response = new StringBuilder();
            String line;
            try {
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
            }
            catch (Exception e) {

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        jsonstring = response.toString();

    }


}