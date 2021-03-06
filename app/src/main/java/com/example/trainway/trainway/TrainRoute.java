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
import android.widget.TextView;
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

public class TrainRoute extends AppCompatActivity {
    private TextView name,no,days,clas;

    private String CurrData;
    private String TrainNumber,URL,APIKEY;
    private EditText UserPnr;
    private Button CheckBtn;
    private ProgressDialog progressDialog;

    RecyclerView recyclerView;
    Context context;
    private List<Station> station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_route);
        APIKEY = "jd8d5dw8vz";

        initialize();


        progressDialog = new ProgressDialog(this);



//        CheckBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                TrainNumber = getIntent().getStringExtra("tno");
                URL= "https://api.railwayapi.com/v2/route/train/"+TrainNumber+"/apikey/jd8d5dw8vz/";
//                https://api.railwayapi.com/v2/route/train/"+PNR+"/apikey/"+APIKEY+"/
                if(validatePNR(TrainNumber))
                {
                    if(isNetworkAvailable())
                    {
                        loadtrainroutes();
                    }
                    else
                        noNetwrokErrorMessage();
                }
                else
                {
                    Toast.makeText(TrainRoute.this,"Enter A Valid Train Number",Toast.LENGTH_LONG).show();
//                    Toast.makeText(PnrStatus.this,PNR,Toast.LENGTH_LONG).show();
                }
//            }
//        });
//
    }

    private void initializeadpter()
    {
        RecyclerViewAdapter2 adapter1 = new RecyclerViewAdapter2(station);
        recyclerView.setAdapter(adapter1);
    }


    private void initialize()
    {
        name = findViewById(R.id.trainroutedetals_trainnameid);
        no = findViewById(R.id.trainroutedetals_trainnoid);
        days = findViewById(R.id.trainroutedetals_runningdaysid);
        clas = findViewById(R.id.trainroutedetals_classesid);


        recyclerView = findViewById(R.id.trainroutedetals_rv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager1);

        RecyclerViewAdapter2 adapter = new RecyclerViewAdapter2(station);

    }

    public boolean isNetworkAvailable(){
//        return  false;
        ConnectivityManager connectivityManager= (ConnectivityManager) TrainRoute.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage(){

        Toast.makeText(TrainRoute.this,"Network Problem",Toast.LENGTH_LONG).show();

    }
    private Boolean validatePNR(String PNR)
    {
        if(PNR.length()!=5)
        {
            return false;
        }
        for(int i=0;i<5;i++)
        {
            char z=PNR.charAt(i);
            if(!(z>='0' && z<='9'))
                return false;
        }
        return true;
    }
    public boolean checkResponseCode(int code)
    {
        return (code == 200);
    }
    public void invalidInputMessage(int code){
        if(code==204) {
            Toast.makeText(TrainRoute.this,"Invalid PNR Number",Toast.LENGTH_LONG).show();
        }
        else if(code==403){
            Toast.makeText(TrainRoute.this,"Request quota for the api is exceeded.",Toast.LENGTH_LONG).show();
        }

        else
        {
            Toast.makeText(TrainRoute.this,"Something went wrong with the server. Please refresh after some time",Toast.LENGTH_LONG).show();

        }

    }


    public void loadtrainroutes(){

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
                            int responseCode=object.getInt("response_code");

                            if(checkResponseCode(responseCode)){

//                                Toast.makeText(TrainRoute.this,"200",Toast.LENGTH_LONG).show();
                                JSONObject tr = object.getJSONObject("train");

                                String trn;
                                CurrData=tr.getString("name");
                                name.setText(CurrData);


                                CurrData =tr.getString("number");
                                no.setText(CurrData);

                                JSONArray days_arr = tr.getJSONArray("days");
                                CurrData = "";
                                String temp;
                                for (int i = 0; i < days_arr.length(); i++)
                                {

                                    temp = days_arr.getJSONObject(i).getString("runs");
                                    if(temp.equals("Y"))
                                    {
                                        CurrData+= days_arr.getJSONObject(i).getString("code")+" ";
                                    }
                                }

                                days.setText(CurrData);

                                JSONArray class_arr = tr.getJSONArray("classes");
                                CurrData = "";
                                for (int i = 0; i < class_arr.length(); i++)
                                {

                                    temp = class_arr.getJSONObject(i).getString("available");
                                    if(temp.equals("Y"))
                                    {
                                        CurrData+= class_arr.getJSONObject(i).getString("code")+" ";
                                    }
                                }
                                clas.setText(CurrData);

                                JSONArray route_arr = object.getJSONArray("route");
                                CurrData = "";
                                int size = route_arr.length();
                                String namee[] = new String[size];
                                String arr[] = new String[size];
                                String dep[] = new String[size];
                                String dista[] = new String[size];
//
//
//
                                String idx,DD;
                                int dist;
                                for (int i = 0; i < route_arr.length(); i++)
                                {
                                    idx = String.valueOf(i+1);
                                    JSONObject station = route_arr.getJSONObject(i).getJSONObject("station");
                                    CurrData =station.getString("name");
                                    namee[i]=CurrData;
                                    CurrData=route_arr.getJSONObject(i).getString("scharr");
                                    arr[i]="Arrival : " + CurrData;

                                    CurrData=route_arr.getJSONObject(i).getString("schdep");
                                    dep[i]="Departure : " + CurrData;

                                    dist = route_arr.getJSONObject(i).getInt("distance");
                                    DD = String.valueOf(dist);
                                    CurrData=DD;
                                    dista[i]="Distance : " + CurrData +" KM";
                                }

                                station = new ArrayList<>();
//
                                for(int i=0;i<size;i++)
                                {
                                    station.add(new Station(namee[i],arr[i],dep[i],dista[i]));
                                }
//                                station.add(new Station("Hello","Hello","Hello","Hello"));

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


                        Toast.makeText(TrainRoute.this,"Some problem has occurred with the server. Please try after some time.",Toast.LENGTH_LONG).show();


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(TrainRoute.this);
        requestQueue.add(request);
    }
}


class Station
{
    String station_name,arrival,depart,distance;
    Station(String s1,String s2,String s3,String s4)
    {
        this.station_name = s1;
        this.arrival = s2;
        this.depart = s3;
        this.distance = s4;
    }
}