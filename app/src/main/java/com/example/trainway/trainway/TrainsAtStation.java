package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrainsAtStation extends AppCompatActivity {

    private EditText StationCode;
    private String StnCode, CurrData, URL;
    private Button Check;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_at_station);


        assign();



        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StnCode = StationCode.getText().toString();
                StnCode.toLowerCase();

                Intent intent = new Intent(TrainsAtStation.this, ShowTrainsAt.class);
                intent.putExtra("stncode", StnCode);
                startActivity(intent);


//                URL = "https://indianrailapi.com/api/v2/AllTrainOnStation/apikey/d9a868f6411e131a285d0df9b32f23ce/StationCode/"+StnCode+"/";
//                if(validate(StnCode))
//                {
//                    if(isNetworkAvailable())
//                    {
//                        loadtrains();
//                    }
//                    else
//                        noNetwrokErrorMessage();
//                }
//                else
//                {
//                    Toast.makeText(TrainsAtStation.this,"Please Enter A Valid Station Code...",Toast.LENGTH_LONG).show();
//                }
            }
        });

    }

    private void assign() {
        StationCode = findViewById(R.id.trainsatstationcode);
        Check = findViewById(R.id.trainsatstationbtn);
        progressDialog = new ProgressDialog(this);
    }
}
