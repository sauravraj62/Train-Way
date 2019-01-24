package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TrainsAtStation extends AppCompatActivity {

    private EditText StationCode;
    private String StnCode,CurrData,URL;
    private Button Check;
    private ListView list;
    ProgressDialog progressDialog;
    ArrayList<String> Data;
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
                URL = "https://indianrailapi.com/api/v2/AllTrainOnStation/apikey/d9a868f6411e131a285d0df9b32f23ce/StationCode/"+StnCode+"/";
                if(validate(StnCode))
                {
                    if(isNetworkAvailable())
                    {
                        loadtrains();
                    }
                    else
                        noNetwrokErrorMessage();
                }
                else
                {
                    Toast.makeText(TrainsAtStation.this,"Please Enter A Valid Station Code...",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private Boolean validate(String str)
    {
        return (str.length()!=0);
    }
    private void assign()
    {
        StationCode = findViewById(R.id.trainsatstationcode);
        Check = findViewById(R.id.trainsatstationbtn);
        list = findViewById(R.id.TrainsAtStationList);
        Data = new ArrayList<String>();
        progressDialog = new ProgressDialog(this);
    }
    public boolean isNetworkAvailable(){
//        return  false;
        ConnectivityManager connectivityManager= (ConnectivityManager) TrainsAtStation.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage(){

        Toast.makeText(TrainsAtStation.this,"Network Problem",Toast.LENGTH_LONG).show();

    }

    public boolean checkResponseCode(int code)
    {
        return (code == 200);
    }
    public void invalidInputMessage(int code){
        if(code==204) {
            Toast.makeText(TrainsAtStation.this,"Invalid PNR Number",Toast.LENGTH_LONG).show();
        }
        else if(code==403){
            Toast.makeText(TrainsAtStation.this,"Request quota for the api is exceeded.",Toast.LENGTH_LONG).show();

        }

        else
        {
            Toast.makeText(TrainsAtStation.this,"Something went wrong with the server. Please refresh after some time",Toast.LENGTH_LONG).show();

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
                                    CurrData += "Train Name = "+trains.getJSONObject(i).getString("TrainName")+" ,";
                                    CurrData += "Departure Time = "+trains.getJSONObject(i).getString("DepartureTime")+" ,";
                                    CurrData += "Arrival Time = "+trains.getJSONObject(i).getString("ArrivalTime")+" ,";
                                    CurrData += "From Station= "+trains.getJSONObject(i).getString("Source")+" ,";
                                    CurrData += "To Station= "+trains.getJSONObject(i).getString("Destination")+" ";
                                    Data.add(CurrData);
                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TrainsAtStation.this,android.R.layout.simple_list_item_1,android.R.id.text1,Data);

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


                        Toast.makeText(TrainsAtStation.this,"Some problem has occurred with the server. Please try after some time.",Toast.LENGTH_LONG).show();


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(TrainsAtStation.this);
        requestQueue.add(request);
    }
}
