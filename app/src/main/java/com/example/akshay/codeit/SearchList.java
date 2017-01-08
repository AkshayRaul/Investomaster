package com.example.akshay.codeit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.akshay.codeit.MainActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;


public class SearchList extends Fragment {

    String TAG="Search";
    View rootView;
    ArrayList<ListObject> arraySearchList;
    public SearchList(){}
    LinearLayoutManager linearLayoutManager;
    ShareListRecyclerAdapter sharelistRecyclerAdapter;
    RecyclerView recyclerView;
    ImageButton imageButton;
    String query;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_share_list, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);

        String[] categories = getResources().getStringArray(R.array.sharelist);
        int[] imageIcons = {R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite};
        arraySearchList = new ArrayList<ListObject>();

        linearLayoutManager = new LinearLayoutManager(getContext());
        sharelistRecyclerAdapter = new ShareListRecyclerAdapter(getContext(), arraySearchList);
        recyclerView.setAdapter(sharelistRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Log.d("Pos",position+"");
                        //pass this intent to displaystock class

                        Intent showStock=new Intent(getContext(),DisplayStock.class);
                        showStock.putExtra("quote",arraySearchList.get(position).category);
                        startActivity(showStock);

                    }
                })
        );
        SearchView search = (SearchView) getActivity().findViewById(R.id.search);
        query=search.getQuery().toString();
        new GetStocks().execute();
        return rootView;
    }

    class GetStocks extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(ShareList.this,"Json Data",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://www.quandl.com/api/v3/datasets.json?query="+query+"&database_code=NSE&per_page=100&page=1&api_key=phLfQVsTFqUqdXQxJxSu";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray stocks = jsonObj.getJSONArray("datasets");

                    // looping through All stocks
                    for (int i = 0; i < stocks.length(); i++) {
                        JSONObject c = stocks.getJSONObject(i);

                        String name = c.getString("name");
                        String datasetcode = c.getString("dataset_code");
                        // adding stock to stock list
                        //listview here
                        arraySearchList.add(new ListObject(datasetcode,"Click to know more"));
                    }
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

