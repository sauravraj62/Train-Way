package com.example.trainway.trainway;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DashBoard_New extends AppCompatActivity {

    RecyclerView recyclerView;
    Context context;
    private List<Items> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board__new);


        recyclerView = (RecyclerView) findViewById(R.id.dashboard_list);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);

        initializeData();
        initializeAdapter();
    }
    private void initializeData(){
        items = new ArrayList<>();
        items.add(new Items("PNR Status", R.drawable.pnr_img));
        items.add(new Items("Train Routes", R.drawable.trainroutes));
        items.add(new Items("Train Fare Enquiry", R.drawable.fare));
        items.add(new Items("Train Between Stations", R.drawable.trainbtwstations));
        items.add(new Items("Train At Station", R.drawable.trainatstation));
        items.add(new Items("Train Live Status", R.drawable.livestatus));
        items.add(new Items("Favorites Trains", R.drawable.heart2));
    }

    private void initializeAdapter(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}

class Items {
    String name;
    int logoId;

    Items(String name, int logoId) {
        this.name = name;
        this.logoId = logoId;
    }
}

