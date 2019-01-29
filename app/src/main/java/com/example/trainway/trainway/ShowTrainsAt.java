package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
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

public class ShowTrainsAt extends AppCompatActivity {


    private String StnCode,CurrData,URL;
    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    Context context;
    private List<Station2> items1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trains_at);

        StnCode = getIntent().getStringExtra("stncode");

        initialize();
        progressDialog = new ProgressDialog(this);

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
                Toast.makeText(ShowTrainsAt.this,"Please Enter A Valid Station Code...",Toast.LENGTH_LONG).show();
            }

    }

    private void initializeadpter()
    {
        RecyclerViewAdapterTrainAt adapter3 = new RecyclerViewAdapterTrainAt(items1);
        recyclerView.setAdapter(adapter3);
    }
    private void initialize()
    {
        recyclerView = findViewById(R.id.trainatstationrv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager3);

        RecyclerViewAdapterTrainAt adapter = new RecyclerViewAdapterTrainAt(items1);

    }




    private Boolean validate(String str)
    {
        return (str.length()!=0);
    }

    public boolean isNetworkAvailable(){
//        return  false;
        ConnectivityManager connectivityManager= (ConnectivityManager) ShowTrainsAt.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage(){

        Toast.makeText(ShowTrainsAt.this,"Network Problem",Toast.LENGTH_LONG).show();

    }

    public boolean checkResponseCode(int code)
    {
        return (code == 200);
    }
    public void invalidInputMessage(int code){
        if(code==204) {
            Toast.makeText(ShowTrainsAt.this,"Invalid PNR Number",Toast.LENGTH_LONG).show();
        }
        else if(code==403){
            Toast.makeText(ShowTrainsAt.this,"Request quota for the api is exceeded.",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(ShowTrainsAt.this,"Something went wrong with the server. Please refresh after some time",Toast.LENGTH_LONG).show();

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

                                String nam[] = new String[size];
                                String noo[] = new String[size];
                                String de[] = new String[size];
                                String ar[] = new String[size];
                                String fr[] = new String[size];
                                String tooo[] = new String[size];

                                for(int i=0;i<trains.length();i++)
                                {
                                    CurrData = "Train Number = "+trains.getJSONObject(i).getString("TrainNo");
                                    nam[i] = CurrData;
                                    CurrData = "Train Name = "+trains.getJSONObject(i).getString("TrainName");
                                    noo[i] = CurrData;
                                    CurrData = "Departure Time = "+trains.getJSONObject(i).getString("DepartureTime");
                                    de[i] = CurrData;
                                    CurrData = "Arrival Time = "+trains.getJSONObject(i).getString("ArrivalTime");
                                    ar[i] = CurrData;
                                    CurrData = "From Station= "+trains.getJSONObject(i).getString("Source");
                                    fr[i] = CurrData;
                                    CurrData = "To Station= "+trains.getJSONObject(i).getString("Destination");
                                    tooo[i] = CurrData;

                                }

                                items1 = new ArrayList<>();
                                for(int i=0;i<size;i++)
                                {
                                    items1.add(new Station2(nam[i],noo[i],ar[i],de[i],fr[i],tooo[i]));
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


                        Toast.makeText(ShowTrainsAt.this,"Some problem has occurred with the server. Please try after some time.",Toast.LENGTH_LONG).show();


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(ShowTrainsAt.this);
        requestQueue.add(request);
    }
}
class Station2
{
    String name,no,arr,dep,from,to;
    Station2(String s1,String s2,String s3,String s4,String s5,String s6)
    {
        this.name = s1;
        this.no = s2;
        this.arr = s3;
        this.dep = s4;
        this.from = s5;
        this.to = s6;
    }
}