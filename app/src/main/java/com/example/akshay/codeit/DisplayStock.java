package com.example.akshay.codeit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by akshay on 7/1/17.
 */
public class DisplayStock extends AppCompatActivity {
    String TAG = "Stock";
    String putTag="SharedP";
    String stock;
    LinearLayout linlaHeaderProgress;
    ImageButton favButton;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.display_stock);
        TextView textView = (TextView) findViewById(R.id.sharenameinfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Stocktoolbar);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        setSupportActionBar(toolbar);
        stock = getIntent().getExtras().getString("quote");
        textView.setText(stock.toString());
        toolbar.setTitle(stock.toString());
         favButton= (ImageButton) findViewById(R.id.favbutton);
        new GetStock().execute();
        WebView webView=(WebView) findViewById(R.id.webv);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.co.in/search?q=NSE:"+stock.toString()+"#fac-ut");
        webView.setScrollContainer(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                Gson gson = new Gson();
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(stock);
                String json = gson.toJson(arrayList);
                editor.putString(putTag, json);
                editor.commit();
                favButton.setVisibility(View.INVISIBLE);
                Log.d("Fav",arrayList.toString());
            }
        });

    }

    private class GetStock extends AsyncTask<Void, Void, Void> {
        String high, low, open, description, date, close;
        TextView datetext, opentext, hightext, lowtext, descriptiontext, closetext;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(ShareList.this,"Json Data",Toast.LENGTH_LONG).show();
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            Log.d("stock", stock);
            String url = "https://www.quandl.com/api/v3/datasets/NSE/" + stock.trim() + ".json?api_key=phLfQVsTFqUqdXQxJxSu";
            String jsonStr = sh.makeServiceCall(url);
            //this is npt working
            Log.d("Stock response", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObj.getJSONObject("dataset").getJSONArray("data");

                    date = jsonArray.getJSONArray(0).get(0).toString();
                    open = jsonArray.getJSONArray(0).get(1).toString();
                    high = jsonArray.getJSONArray(0).get(2).toString();
                    low = jsonArray.getJSONArray(0).get(3).toString();
                    close = jsonArray.getJSONArray(0).get(4).toString();
                    Log.d("Array", "" + jsonArray.getJSONArray(0).get(0));
                    Log.d("Array", "" + jsonArray.getJSONArray(0).get(1));
                    Log.d("Array", "" + jsonArray.getJSONArray(0).get(2));
                    Log.d("Array", "" + jsonArray.getJSONArray(0).get(3));


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            datetext = (TextView) findViewById(R.id.dateinfo);
            datetext.setText(date);
            opentext = (TextView) findViewById(R.id.openinfo);
            opentext.setText(open);
            hightext = (TextView) findViewById(R.id.highinfo);
            hightext.setText(high);
            lowtext = (TextView) findViewById(R.id.lowinfo);
            lowtext.setText(low);
            closetext = (TextView) findViewById(R.id.close);
            closetext.setText(close);
            linlaHeaderProgress.setVisibility(View.GONE);

        }

    }

}

