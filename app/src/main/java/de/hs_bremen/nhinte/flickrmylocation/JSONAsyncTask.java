package de.hs_bremen.nhinte.flickrmylocation;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONAsyncTask extends AsyncTask<String, Void, Boolean>{
    private String bufferdResult = "";



    private String imageUrl = "";

    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(String... urls) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        ByteArrayOutputStream outString = new ByteArrayOutputStream();
        int numRead;
        final int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];


        try {
            url = new URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=e07a1743a12566b5e20dd17eae2a295e&tags=City%2CLandscape%2CNature&geo_context=0&lat=53.0833333&lon=8.8&10&format=json&per_page=10&page=1&nojsoncallback=1");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            while ((numRead = in.read(buffer)) != -1) {
                outString.write(buffer, 0, numRead);
            }

            System.out.println("worked -> " + in);
            return true;
        } catch (MalformedURLException  e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {

            try {
                JSONObject jsonObj = new JSONObject(outString.toString());
                JSONObject urlObj = new JSONObject(jsonObj.getJSONObject("photos").getJSONArray("photo").get(0).toString());
                //System.out.println(jsonObj.getJSONObject("photos").getJSONArray("photo").get(1).toString());
               // getURLFromJson(urlObj);
                System.out.println(urlObj.toString());


            } catch (JSONException e){
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }


            urlConnection.disconnect();
        }
        return false;
    }

    public void print(){
        System.out.println("" + bufferdResult);
    }

    public void getURLFromJson(JSONObject jsonData){
        String farm = "";
     //   String serverId = "";
        System.out.println(jsonData.toString());
        try{
             farm = jsonData.getString("farm");
          //  serverId = jsonData.getString("server");
        } catch (JSONException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

      //  System.out.println("JSON URL ->" + "https://farm" + farm + ".staticflickr.com/" + serverId);
    }

   // {server-id}/{id}_{secret}.jpg

    public String getImageUrl() {
        return imageUrl;
    }

    protected void onPostExecute(Boolean result) {

    }
}