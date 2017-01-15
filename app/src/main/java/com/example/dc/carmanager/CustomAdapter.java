package com.example.dc.carmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends ArrayAdapter<JSONObject> {

    public CustomAdapter(Context context, ArrayList<JSONObject> allJSONObjects) {
        super(context, R.layout.custom_row, allJSONObjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row, parent, false);

        String text = "empty";
        try {
            text = getItem(position).get("NAME").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView nameTextView = (TextView) customView.findViewById(R.id.nameTextView);
        nameTextView.setText(text);

        return customView;
    }
}