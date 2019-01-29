package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
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

public class FareQuery extends AppCompatActivity {
    private String source,destination,trainnumber,CurrData,URL;
    private ProgressDialog progressDialog;

    RecyclerView recyclerView;
    Context context;
    private List<Items1> items1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_query);

        initialize();

        progressDialog = new ProgressDialog(this);


//        Check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                source = getIntent().getStringExtra("scode");
                destination = getIntent().getStringExtra("dcode");
                trainnumber = getIntent().getStringExtra("tno");

                source.toUpperCase();
                destination.toUpperCase();
                URL = "https://indianrailapi.com/api/v2/TrainFare/apikey/d9a868f6411e131a285d0df9b32f23ce/TrainNumber/"+trainnumber+"/From/"+source+"/To/"+destination+"/Quota/GN";
                if(validate(source,destination,trainnumber))
                {
                    if(isNetworkAvailable())
                    {
                        loadfare();
//                        Toast.makeText(FareQuery.this,"Hulululululu",Toast.LENGTH_LONG).show();

                    }
                    else
                        noNetwrokErrorMessage();
                }
                else
                {
                    Toast.makeText(FareQuery.this,"Please Enter Valid Details...",Toast.LENGTH_LONG).show();
                }
//            }
//        });

    }
    private void initializeadpter()
    {
        RecyclerViewAdapterFare adapter1 = new RecyclerViewAdapterFare(items1);
        recyclerView.setAdapter(adapter1);
    }
    private void initialize()
    {
        recyclerView = findViewById(R.id.farerv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager1);

        RecyclerViewAdapterFare adapter = new RecyclerViewAdapterFare(items1);

    }
    private Boolean validate(String sr,String des,String tr)
    {
        if(sr.length()==0 || des.length()==0 || tr.length()==0)
            return false;

        if(tr.length()!=5)
            return false;
        return  true;
    }
    public boolean isNetworkAvailable(){
//        return  false;
        ConnectivityManager connectivityManager= (ConnectivityManager) FareQuery.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage(){

        Toast.makeText(FareQuery.this,"Network Problem",Toast.LENGTH_LONG).show();

    }
    public boolean checkResponseCode(int code)
    {
        return (code == 200);
    }
    public void invalidInputMessage(int code){
        if(code==204) {
            Toast.makeText(FareQuery.this,"Invalid PNR Number",Toast.LENGTH_LONG).show();
        }
        else if(code==403){
            Toast.makeText(FareQuery.this,"Request quota for the api is exceeded.",Toast.LENGTH_LONG).show();

        }

        else
        {
            Toast.makeText(FareQuery.this,"Something went wrong with the server. Please refresh after some time",Toast.LENGTH_LONG).show();

        }

    }
    public void loadfare(){

        String url=URL;
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
//                        ArrayList<String> status= new ArrayList<>();
                        try {
                            JSONObject object= new JSONObject(response);
                            int responseCode=object.getInt("ResponseCode");

                            if(checkResponseCode(responseCode)){

                                items1 = new ArrayList<>();


                                CurrData ="Train Name = "+ object.getString("TrainName");
                                items1.add(new Items1(CurrData));

                                CurrData ="From Station = "+ object.getString("From");
                                items1.add(new Items1(CurrData));

                                CurrData ="To Station = "+ object.getString("To");
                                items1.add(new Items1(CurrData));

                                CurrData ="Distance = "+ object.getString("Distance");
                                items1.add(new Items1(CurrData));

                                CurrData ="Train Type = "+ object.getString("TrainType");
                                items1.add(new Items1(CurrData));


                                CurrData = "  ";
                                CurrData = "Fares Are As Follows:";
                                items1.add(new Items1(CurrData));

                                JSONArray fares = object.getJSONArray("Fares");
                                for(int i=0;i<fares.length();i++)
                                {
                                    CurrData = fares.getJSONObject(i).getString("Name")+ "= "+fares.getJSONObject(i).getString("Fare");
                                    items1.add(new Items1(CurrData));
                                }


                                initializeadpter();

                            }
                            else
                            {
                                invalidInputMessage(responseCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();


                        Toast.makeText(FareQuery.this,"Some problem has occurred with the server. Please try after some time.",Toast.LENGTH_LONG).show();


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(FareQuery.this);
        requestQueue.add(request);
    }
}

class Items1
{
    String description;
    Items1(String s1)
    {
        this.description = s1;
    }
}