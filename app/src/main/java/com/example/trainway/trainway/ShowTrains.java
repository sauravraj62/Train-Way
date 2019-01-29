package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
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

public class ShowTrains extends AppCompatActivity {
    String source,destination,URL,CurrData;
    private ProgressDialog progressDialog;

    RecyclerView recyclerView;
    Context context;
    private List<Station1> items1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trains);

        source = getIntent().getStringExtra("src");
        destination = getIntent().getStringExtra("dest");
        source.toLowerCase();
        destination.toLowerCase();
        URL ="https://indianrailapi.com/api/v2/TrainBetweenStation/apikey/d9a868f6411e131a285d0df9b32f23ce/From/"+source+"/To/"+destination;
//        "https://api.railwayapi.com/v2/between/source/gkp/dest/jat/date/24-06-2017/apikey/myapikey/"

        initialize();
        progressDialog = new ProgressDialog(this);
        if(isNetworkAvailable())
        {
            loadtrains();
        }
        else
            noNetwrokErrorMessage();

    }

    private void initializeadpter()
    {
        RecyclerViewAdapterBtw adapter2 = new RecyclerViewAdapterBtw(items1);
        recyclerView.setAdapter(adapter2);
    }
    private void initialize()
    {
        recyclerView = findViewById(R.id.btwrv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager2);

        RecyclerViewAdapterBtw adapter = new RecyclerViewAdapterBtw(items1);

    }

    public boolean isNetworkAvailable(){
//        return  false;
        ConnectivityManager connectivityManager= (ConnectivityManager) ShowTrains.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage(){

        Toast.makeText(ShowTrains.this,"Network Problem",Toast.LENGTH_LONG).show();

    }
    public boolean checkResponseCode(int code)
        {
            return (code == 200);
        }
        public void invalidInputMessage(int code){
            if(code==204) {
                Toast.makeText(ShowTrains.this,"Invalid PNR Number",Toast.LENGTH_LONG).show();
            }
            else if(code==403){
                Toast.makeText(ShowTrains.this,"Request quota for the api is exceeded.",Toast.LENGTH_LONG).show();

            }

            else
            {
                Toast.makeText(ShowTrains.this,"Something went wrong with the server. Please refresh after some time",Toast.LENGTH_LONG).show();

            }

    }
    public void loadtrains(){

        String url=URL;
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        String trainNo="";
                        String trainName="";

//                        ArrayList<String> status= new ArrayList<>();
                        try {
                            JSONObject object= new JSONObject(response);
                            int responseCode=object.getInt("ResponseCode");

                            if(checkResponseCode(responseCode)){

                                JSONArray trains = object.getJSONArray("Trains");

                                int size = trains.length();

                                String tnam[] = new String[size];
                                String tno[] = new String[size];
                                String tarr[] = new String[size];
                                String tdep[] = new String[size];
                                String ttime[] = new String[size];
                                String ttype[] = new String[size];

                                for(int i=0;i<trains.length();i++)
                                {
                                    CurrData = "Train Number = "+trains.getJSONObject(i).getString("TrainNo")+" ,";
                                    tnam[i] = CurrData;
                                    CurrData = "Train Name = "+trains.getJSONObject(i).getString("TrainName")+" ";
                                    tno[i] = CurrData;
                                    CurrData = "Departure Time = "+trains.getJSONObject(i).getString("DepartureTime")+" ";
                                    tdep[i] = CurrData;
                                    CurrData = "Arrival Time = "+trains.getJSONObject(i).getString("ArrivalTime")+" ";
                                    tarr[i] = CurrData;
                                    CurrData = "Travel Time = "+trains.getJSONObject(i).getString("TravelTime")+" ";
                                    ttime[i] = CurrData;
                                    CurrData = "Train Type= "+trains.getJSONObject(i).getString("TrainType")+" ";
                                    ttype[i] = CurrData;
                                }
                                items1 = new ArrayList<>();

                                for(int i=0;i<size;i++)
                                {
                                    items1.add(new Station1(tnam[i],tno[i],tarr[i],tdep[i],ttime[i],ttype[i]));
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


                        Toast.makeText(ShowTrains.this,"Some problem has occurred with the server. Please try after some time.",Toast.LENGTH_LONG).show();


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(ShowTrains.this);
        requestQueue.add(request);
    }
}
class Station1
{
    String name,no,arr,dep,time,type;
    Station1(String s1,String s2,String s3,String s4,String s5,String s6)
    {
        this.name = s1;
        this.no = s2;
        this.arr = s3;
        this.dep = s4;
        this.time = s5;
        this.type = s6;
    }
}
