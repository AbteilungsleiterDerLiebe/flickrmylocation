package de.hs_bremen.nhinte.flickrmylocation;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONAsyncTask extends AsyncTask<String, String, String>{

    protected void onPreExecute() {
        super.onPreExecute();
    }

    private Context context;
    private String targetUrl;

    public JSONAsyncTask(Context context, String targetUrl){
        this.context = context;
        this.targetUrl = targetUrl;
    }

    @Override
    protected String doInBackground(String... urls) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        ByteArrayOutputStream outString = new ByteArrayOutputStream();
        int numRead;
        final int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];


        try {
            url = new URL(targetUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            while ((numRead = in.read(buffer)) != -1) {
                outString.write(buffer, 0, numRead);
            }
            return outString.toString();
        } catch (MalformedURLException  e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }

    public String getURLFromJson(JSONObject jsonData){
        String farm = "";
        String serverId = "";
        String id = "";
        String secret = "";
        String finalUrl = "";

        try{
            farm = jsonData.getString("farm");
            serverId = jsonData.getString("server");
            id = jsonData.getString("id");
            secret = jsonData.getString("secret");
        } catch (JSONException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        finalUrl = "https://farm" + farm + ".staticflickr.com/" + serverId + "/" + id + "_" + secret + ".jpg";
        return finalUrl;
    }

    protected void onPostExecute(String result) {
        String url = "";

        try {
            JSONObject jsonObj = new JSONObject(result.toString());
            JSONObject urlObj = new JSONObject(jsonObj.getJSONObject("photos").getJSONArray("photo").get(0).toString());
            url = getURLFromJson(urlObj);
        } catch (JSONException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("result = " + url);


        ImageView aTextView = (ImageView) ((Activity)context).findViewById(R.id.testImage);

        Picasso.with(context).load(url).into((ImageView)  aTextView);

    }
}

//  "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=e07a1743a12566b5e20dd17eae2a295e&tags=City%2CLandscape%2CNature&geo_context=0&lat=53.0833333&lon=8.8&10&format=json&per_page=10&page=1&nojsoncallback=1"