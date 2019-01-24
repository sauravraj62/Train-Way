package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ShowTrains extends AppCompatActivity {
    String source,destination,URL,CurrData;
    private ProgressDialog progressDialog;
    private ArrayList<String> Data;
    private ListView list;
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

        progressDialog = new ProgressDialog(this);
        list = findViewById(R.id.trainslist);

        Data = new ArrayList<String>();
        Data.clear();
        if(isNetworkAvailable())
        {
            loadtrains();
        }
        else
            noNetwrokErrorMessage();

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

                                for(int i=0;i<trains.length();i++)
                                {
                                    CurrData = "Train Number = "+trains.getJSONObject(i).getString("TrainNo")+" ,";
                                    CurrData += "Train Name = "+trains.getJSONObject(i).getString("TrainName")+" ";
                                    CurrData += "Departure Time = "+trains.getJSONObject(i).getString("DepartureTime")+" ";
                                    CurrData += "Arrival Time = "+trains.getJSONObject(i).getString("ArrivalTime")+" ";
                                    CurrData += "Travel Time = "+trains.getJSONObject(i).getString("TravelTime")+" ";
                                    CurrData += "Train Type= "+trains.getJSONObject(i).getString("TrainType")+" ";
                                    Data.add(CurrData);
                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowTrains.this,android.R.layout.simple_list_item_1,android.R.id.text1,Data);

                                list.setAdapter(adapter);


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
