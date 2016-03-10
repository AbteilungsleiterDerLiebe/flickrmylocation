package de.hs_bremen.nhinte.flickrmylocation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createUrlTextfield();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void createUrlTextfield() {
        TextView test = (TextView) findViewById(R.id.testUrl);
        test.setText("workz");
       FlickrApiQueryManager flickrApiQueryManager = new FlickrApiQueryManager();
       String asd = flickrApiQueryManager.createURL();
        String testString = "https://farm1.staticflickr.com/411/19448655899_18d4b0c156.jpg";
        test.setText("" + asd);
       //View v = (ImageView) findViewById(R.id.testImage);
        Picasso.with(this).load(testString).into((ImageView) findViewById(R.id.testImage));

       // JSONAsyncTask js = new JSONAsyncTask();
        new JSONAsyncTask().execute();
       // test.setText("" + js.getBufferdResult());
    }
}



