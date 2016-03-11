package de.hs_bremen.nhinte.flickrmylocation;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.HorizontalScrollView;
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
import java.util.ArrayList;

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
        String url = "";
        String url2 = "";
        String url3 = "";
        String url4 = "";
        String url5 = "";

        try {
            JSONObject jsonObj = new JSONObject(result.toString());
            JSONObject urlObj = new JSONObject(jsonObj.getJSONObject("photos").getJSONArray("photo").get(0).toString());

            ArrayList<String> myURLs = parseJSON(jsonObj);
            System.out.println(" ARRAY: " + myURLs.get(1).toString());
            System.out.println(" ARRAY: " + myURLs.get(0).toString());
            System.out.println(" ARRAY: " + myURLs.get(2).toString());
            System.out.println(" ARRAY: " + myURLs.get(3).toString());

            url = myURLs.get(0).toString();
            url2 = myURLs.get(1).toString();
            url3 = myURLs.get(2).toString();
            url4 = myURLs.get(3).toString();
            url5 = myURLs.get(4).toString();

            url = getURLFromJson(urlObj);
        } catch (JSONException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        HorizontalScrollView hView = (HorizontalScrollView) ((Activity)context).findViewById(R.id.horizontalScrollView);
        int color = Color.argb(255, 0, 0, 0);
        hView.setBackgroundColor(color);

        int screenHight = hView.getHeight();

        ImageView aTextView = (ImageView) ((Activity)context).findViewById(R.id.testImage);
        Picasso.with(context).load(url).resize(0,screenHight).into((ImageView) aTextView);

        ImageView aTextView2 = (ImageView) ((Activity)context).findViewById(R.id.testImage2);
        Picasso.with(context).load(url2).resize(0,screenHight).into((ImageView) aTextView2);

        ImageView aTextView3 = (ImageView) ((Activity)context).findViewById(R.id.testImage3);
        Picasso.with(context).load(url3).resize(0, screenHight).into((ImageView) aTextView3);

        ImageView aTextView4 = (ImageView) ((Activity)context).findViewById(R.id.testImage4);
        Picasso.with(context).load(url4).resize(0, screenHight).into((ImageView) aTextView4);

        ImageView aTextView5 = (ImageView) ((Activity)context).findViewById(R.id.testImage5);
        Picasso.with(context).load(url5).resize(0,screenHight).into((ImageView) aTextView5);



    }
}