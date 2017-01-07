package com.example.akshay.codeit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ShareList extends Fragment {

    View rootView;
    ArrayList<ListObject> arrayList;
    public ShareList(){}
    LinearLayoutManager linearLayoutManager;
    ShareListRecyclerAdapter ewrRecyclerAdapter;
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_share_list, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);

        String[] categories = getResources().getStringArray(R.array.sharelist);
        int[] imageIcons = {R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite};

        arrayList = new ArrayList<>();

        for (int i = 0; i < categories.length; i++) {
            arrayList.add(new ListObject(categories[i],imageIcons[i]));

        }

        linearLayoutManager = new LinearLayoutManager(getContext());
        ewrRecyclerAdapter = new ShareListRecyclerAdapter(getContext(), arrayList);
        recyclerView.setAdapter(ewrRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return rootView;
    }



}
