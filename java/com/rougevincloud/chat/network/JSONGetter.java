package com.rougevincloud.chat.network;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class JSONGetter extends AsyncTask<Void, Void, JSONObject> {
    private final URL url;

    public JSONGetter(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        HttpURLConnection connection;
        JSONObject jObject = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                String msg = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
                jObject = new JSONObject(msg);

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jObject;
    }
}
