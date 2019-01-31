package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteTrains extends AppCompatActivity {

    private String source,destination,URL,CurrData,status;
//    private ProgressDialog progressDialog;

    RecyclerView recyclerView;
    Context context;
    private List<Favtrains> trains;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_trains);

//        Toast.makeText(FavoriteTrains.this,"Entered",Toast.LENGTH_LONG).show();
        initialize();
        loaddata();

//        progressDialog = new ProgressDialog(this);
    }

    private void loaddata()
    {
        SharedPreferences prefA = getSharedPreferences("favTrains",MODE_PRIVATE);

        trains = new ArrayList<>();

        Map<String, ?> allEntries = prefA.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            status = entry.getValue().toString();
            if(status.equals("Yes"))
            {
                CurrData = ""+ entry.getKey();
                trains.add(new Favtrains(CurrData));

            }


//            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        initializeadpter();
    }
    private void initializeadpter()
    {
        RecyclerViewAdapterFav adapter4 = new RecyclerViewAdapterFav(trains);
        recyclerView.setAdapter(adapter4);
    }
    private void initialize()
    {
        recyclerView = findViewById(R.id.favrv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager4);

        RecyclerViewAdapterFav adapter = new RecyclerViewAdapterFav(trains);

    }
}
class Favtrains
{
    String Number;
    Favtrains(String str)
    {
        this.Number = str;
    }
}