package de.hs_bremen.nhinte.flickrmylocation;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.ArrayList;

public class JSONAsyncTask extends AsyncTask<String, String, String>{

    protected void onPreExecute() {
        super.onPreExecute();
    }

    // context from main activity
    private Context context;

    // target URL for API query
    private String targetUrl;

    public JSONAsyncTask(Context context, String targetUrl){
        this.context = context;
        this.targetUrl = targetUrl;
    }

    @Override
    protected String doInBackground(String... urls) {

        // init parameters for http connection
        URL url;
        HttpURLConnection urlConnection = null;
        ByteArrayOutputStream outString = new ByteArrayOutputStream();
        int numRead;
        final int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // Opens http stream with flickr
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
        return "ERROR";
    }

    public String getURLFromJson(JSONObject jsonData){
        String farm = "";
        String serverId = "";
        String id = "";
        String secret = "";

        try{
            farm = jsonData.getString("farm");
            serverId = jsonData.getString("server");
            id = jsonData.getString("id");
            secret = jsonData.getString("secret");
        } catch (JSONException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return "https://farm" + farm + ".staticflickr.com/" + serverId + "/" + id + "_" + secret + ".jpg";
    }

    public ArrayList<String> parseJSON(JSONObject vars){

        JSONObject URLs = vars;

        ArrayList<String> myURLs = new ArrayList<String>();
        try {

            for(int i = 0; i < URLs.getJSONObject("photos").getJSONArray("photo").length(); i++){
                JSONObject tmpJSON = new JSONObject(URLs.getJSONObject("photos").getJSONArray("photo").get(i).toString());
                myURLs.add(getURLFromJson(tmpJSON));
            }

        } catch (JSONException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }


        return myURLs;
    }

    protected void onPostExecute(String result) {

        HorizontalScrollView hView = (HorizontalScrollView) ((Activity)context).findViewById(R.id.horizontalScrollView);
        int screenHight = hView.getHeight();

        // show error png if there's no connection/API isnt responding
        if(result == "ERROR"){
            ImageView iv = new ImageView(this.context);
            Picasso.with(context).load(R.drawable.networkerror).resize(0, screenHight).into( iv);
            LinearLayout imageLayout = (LinearLayout) ((Activity)context).findViewById(R.id.imageLayout);
            imageLayout.addView(iv);
        } else {
            try {
                JSONObject jsonObj = new JSONObject(result.toString());
                ArrayList<String> myURLs = parseJSON(jsonObj);

                if(myURLs!=null){
                    //prints pictures
                    for(String object: myURLs){
                        ImageView iv = new ImageView(this.context);
                        iv.setPadding(5,5,5,5);
                        Picasso.with(context).load(object).resize(0, screenHight).into( iv);
                        LinearLayout imageLayout = (LinearLayout) ((Activity)context).findViewById(R.id.imageLayout);
                        imageLayout.addView(iv);
                    }
                }
            } catch (JSONException e){
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }
}