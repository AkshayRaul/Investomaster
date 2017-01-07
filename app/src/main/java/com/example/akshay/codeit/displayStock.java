package com.example.akshay.codeit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akshay on 7/1/17.
 */
public class displayStock extends AppCompatActivity{

    String TAG="Stock";
    String stock;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        stock= getIntent().getExtras().getString("quote");
        new GetStock().execute();

    }
    private class GetStock extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(ShareList.this,"Json Data",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://www.quandl.com/api/v3/datasets/NSE/"+stock.trim()+".json?api_key=phLfQVsTFqUqdXQxJxSu";
            String jsonStr = sh.makeServiceCall(url);

            Log.d("Stock response", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray stocks = jsonObj.getJSONArray("datasets");

                    // looping through All stocks


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

        }
    }

}
