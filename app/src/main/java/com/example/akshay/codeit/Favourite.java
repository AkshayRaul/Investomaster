package com.example.akshay.codeit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class Favourite extends Fragment {

    String TAG = "Search";
    String putTag="SharedP";
    View rootView;
    ArrayList<ListObject> arraySearchList;

    public Favourite() {
    }

    LinearLayoutManager linearLayoutManager;
    FavListRecyclerAdapter sharelistRecyclerAdapter;
    RecyclerView recyclerView;
    ImageButton imageButton;
    String query;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fav_fragment_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.favRecyclerView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString(putTag, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        final ArrayList<String> arrayList = gson.fromJson(json, type);
        if(arrayList.size()==0){
            Toast.makeText(getContext(),"No Favourites Added",Toast.LENGTH_LONG);
        }
        else {
            linearLayoutManager = new LinearLayoutManager(getContext());
            sharelistRecyclerAdapter = new FavListRecyclerAdapter(getContext(), arrayList);
            recyclerView.setAdapter(sharelistRecyclerAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click
                            Log.d("Pos", position + "");
                            //pass this intent to displaystock class

                            Intent showStock = new Intent(getContext(), DisplayStock.class);
                            showStock.putExtra("quote", arrayList.get(position));
                            startActivity(showStock);

                        }
                    })
            );
        }
        SearchView search = (SearchView) getActivity().findViewById(R.id.search);
        query = search.getQuery().toString();
        return rootView;
    }
}



