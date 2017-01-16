package com.example.dc.carmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<JSONObject> {

    public CustomAdapter(Context context, ArrayList<JSONObject> allJSONObjects) {
        super(context, R.layout.custom_row, allJSONObjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row, parent, false);

        // Get type
        String type = "-1";
        try {
            type = getItem(position).get("type").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Display name
        String name = "-1";
        try {
            name = getItem(position).get("name").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView nameTextView = (TextView) customView.findViewById(R.id.nameTextView);
        nameTextView.setText(name);

        // Display address
        String address = "-1";
        try {
            address = getItem(position).get("address").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView addressTextView = (TextView) customView.findViewById(R.id.addressTextView);
        addressTextView.setText(address);

        // Display coordinates
        String lat = "-1";
        String lon = "-1";
        String coordinates = "";
        try {
            lat = getItem(position).get("lat").toString();
            lon = getItem(position).get("lon").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        coordinates = lat + " / " + lon;
        TextView coordinatesTextView = (TextView) customView.findViewById(R.id.coordTextView);
        coordinatesTextView.setText(coordinates);

        // Display capacity, if available
        String capacity = "-1";
        try {
            capacity = getItem(position).get("capacity").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView capacityTextView = (TextView) customView.findViewById(R.id.capacityTextView);
        if (capacity.equals("-1")) {
            capacityTextView.setText(" ");
        }
        else {
            capacityTextView.setText("Capactiy: " + capacity);
        }

        // Display icon, if available
        String icon = "-1";
        try {
            icon = getItem(position).get("icon").toString();
        } catch (JSONException e) {
            e.printStackTrace();
            icon = "-1";
        }

        ImageView iconImageView = (ImageView) customView.findViewById(R.id.iconImageView);

        if (icon.equals("-1")) {
            if (type.equals("fuel")) {
                iconImageView.setImageResource(R.drawable.fuel);
            }
            else if (type.equals("pub")) {
                iconImageView.setImageResource(R.drawable.pub);
            }
            else {
                Toast.makeText(getContext(), ("type = " + type), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            DownloadImageTask imgdl = new DownloadImageTask(icon);
            imgdl.start();
            /* try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } */

            while (imgdl.isAlive()) {

            }
            iconImageView.setImageBitmap(imgdl.getBitmap());
        }

        return customView;
    }
}