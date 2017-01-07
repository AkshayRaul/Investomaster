package com.example.akshay.codeit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHWETHA on 06-01-2017.
 */
public class ShareListRecyclerAdapter extends RecyclerView.Adapter<ShareListRecyclerViewHolder>  {

    private Context context;
   ImageButton imageButton;
    private ArrayList<ListObject> arrayList = new ArrayList<>();

    //constructor
    public ShareListRecyclerAdapter(Context context, ArrayList<ListObject> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    //uncomment the following and add your own layout

    @Override
    public ShareListRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //creates a RecyclerViewHolderHospitals that holds a view of R.layout.recycler_list_item type

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.recycler_list_item, parent, false);
        imageButton= (ImageButton)itemView.findViewById(R.id.favbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Added To Favourites",
                        Toast.LENGTH_SHORT).show();


            }
        });
        // itemView.setOnClickListener(this);
        return new ShareListRecyclerViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ShareListRecyclerViewHolder holder, int position) {
        //set layout resources here from arraylist
        holder.categoryText.setText(arrayList.get(position).category);
        holder.imageIcon.setImageResource(arrayList.get(position).image);

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}

 class ShareListRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public Context context;
    @Bind(R.id.favbutton)
    ImageView imageIcon;

    @Bind(R.id.sharename)
    TextView categoryText;

    public ShareListRecyclerViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        View r=itemView;
        ButterKnife.bind(this, itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
    }


}







