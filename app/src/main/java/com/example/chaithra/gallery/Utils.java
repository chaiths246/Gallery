package com.example.chaithra.gallery;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chaithra on 3/26/18.
 */

public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static ArrayList<Gallery> fetchMeowData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        ArrayList<Gallery> newslist = null;
        try {
            newslist = jsonconverter(jsonResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newslist;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                Log.d("Network", "" + line);
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Gallery> jsonconverter(String newsJSON) throws ParseException {
        ArrayList<Gallery> newsarraylist = new ArrayList<Gallery>();
        String result = "";
        try {


            JSONObject newJsonObject=new JSONObject(newsJSON);
            JSONObject photos=newJsonObject.getJSONObject("photos");
            JSONArray photo=photos.getJSONArray("photo");
            for (int i = 0; i < photo.length(); i++) {
                JSONObject e = photo.getJSONObject(i);

                //String image = e.getString("image_url");
                String title=e.getString("title");
                int farmId=e.getInt("farm");
                String farm1=String.valueOf(farmId);
                String serverId=e.getString("server");
                String photoId=e.getString("id");
                String secret=e.getString("secret");
                String imageUrl= "http://farm" + farm1 + ".static.flickr.com/"
                        + serverId + "/" + photoId + "_" + secret + "_m.jpg";
                newsarraylist.add(new Gallery(imageUrl, title));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return newsarraylist;
    }
}
