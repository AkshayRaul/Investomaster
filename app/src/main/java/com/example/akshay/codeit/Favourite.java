package com.example.akshay.codeit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by SHWETHA on 08-01-2017.
 */
/*
public class Favourite extends AppCompatActivity {
    static ArrayList<String>Fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_list);

}}
*/
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class Favourite extends AppCompatActivity {

    String TAG="Search";
    View rootView;

    public Favourite(){}
    LinearLayoutManager linearLayoutManager;
    FavListRecyclerAdapter favlistRecyclerAdapter;
    RecyclerView recyclerView;
    String query;
    static ArrayList<String>myFavStocks =new ArrayList<String>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_list);
        rootView = inflater.inflate(R.layout.fav_fragment_list, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.favRecyclerView);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> arrayList = gson.fromJson(json, type);
        linearLayoutManager = new LinearLayoutManager(getContext());
        favlistRecyclerAdapter = new FavListRecyclerAdapter(getContext(), arrayList);
        recyclerView.setAdapter(favlistRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Log.d("Pos",position+"");
                        //pass this intent to displaystock class

                        Intent showStock=new Intent(getContext(),DisplayStock.class);
                        showStock.putExtra("quote",myFavStocks.get(position));
                        startActivity(showStock);

                    }
                })
        );
        return rootView;
    }

}

