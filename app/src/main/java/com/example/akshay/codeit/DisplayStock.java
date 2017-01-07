package com.example.akshay.codeit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

/**
 * Created by akshay on 7/1/17.
 */
public class DisplayStock extends AppCompatActivity{
    String TAG="Stock";
    String stock;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.display_stock);
        TextView textView=(TextView)findViewById(R.id.sharenameinfo) ;
        Toolbar toolbar = (Toolbar) findViewById(R.id.Stocktoolbar);
        setSupportActionBar(toolbar);

        stock= getIntent().getExtras().getString("quote");
        textView.setText(stock.toString());
        toolbar.setTitle(stock.toString());
        new GetStock().execute();

    }
    private class GetStock extends AsyncTask<Void, Void, Void> {
        String high,low,open,description,date,close;
        TextView datetext,opentext,hightext,lowtext,descriptiontext,closetext;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(ShareList.this,"Json Data",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            Log.d("stock",stock);
            String url = "https://www.quandl.com/api/v3/datasets/NSE/"+stock.trim()+".json?api_key=phLfQVsTFqUqdXQxJxSu";
            String jsonStr = sh.makeServiceCall(url);
            //this is npt working
            Log.d("Stock response", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArray=jsonObj.getJSONObject("dataset").getJSONArray("data");

                    date=jsonArray.getJSONArray(0).get(0).toString();
                    open=jsonArray.getJSONArray(0).get(1).toString();
                    high=jsonArray.getJSONArray(0).get(2).toString();
                    low=jsonArray.getJSONArray(0).get(3).toString();
                    close=jsonArray.getJSONArray(0).get(4).toString();
                    Log.d("Array",""+jsonArray.getJSONArray(0).get(0));
                    Log.d("Array",""+jsonArray.getJSONArray(0).get(1));
                    Log.d("Array",""+jsonArray.getJSONArray(0).get(2));
                    Log.d("Array",""+jsonArray.getJSONArray(0).get(3));



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
            datetext=(TextView) findViewById(R.id.dateinfo);
            datetext.setText(date);
            opentext=(TextView) findViewById(R.id.openinfo);
            opentext.setText(open);
            hightext=(TextView) findViewById(R.id.highinfo);
            hightext.setText(high);
            lowtext=(TextView) findViewById(R.id.lowinfo);
            lowtext.setText(low);
            closetext=(TextView) findViewById(R.id.close);
            closetext.setText(close);

        }
    }

}
