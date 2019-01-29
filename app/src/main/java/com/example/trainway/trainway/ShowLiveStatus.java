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

public class ShowLiveStatus extends AppCompatActivity {

    private String trainno,stationcode,date,URL,CurrData;
    private ProgressDialog progressDialog;
    private ArrayList<String> Data;
    private ListView list;
    private Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_live_status);
        trainno = getIntent().getStringExtra("trainno");
        stationcode = getIntent().getStringExtra("stationcode");
        date = getIntent().getStringExtra("date");
        stationcode.toUpperCase();

        URL ="https://api.railwayapi.com/v2/live/train/"+trainno+"/station/"+stationcode+"/date/"+date+"/apikey/jd8d5dw8vz/";


        progressDialog = new ProgressDialog(this);
        list = findViewById(R.id.livestatuslist);

        Data = new ArrayList<String>();
        Data.clear();
        if(isNetworkAvailable())
        {
            loadstatus();
        }
        else
            noNetwrokErrorMessage();
    }
    public boolean isNetworkAvailable(){
//        return  false;
        ConnectivityManager connectivityManager= (ConnectivityManager) ShowLiveStatus.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage(){

        Toast.makeText(ShowLiveStatus.this,"Network Problem",Toast.LENGTH_LONG).show();

    }
    public boolean checkResponseCode(int code)
    {
        return (code == 200);
    }
    public void invalidInputMessage(int code){
        if(code==204) {
            Toast.makeText(ShowLiveStatus.this,"Invalid PNR Number",Toast.LENGTH_LONG).show();
        }
        else if(code==403){
            Toast.makeText(ShowLiveStatus.this,"Request quota for the api is exceeded.",Toast.LENGTH_LONG).show();

        }

        else
        {
            Toast.makeText(ShowLiveStatus.this,"Something went wrong with the server. Please refresh after some time",Toast.LENGTH_LONG).show();

        }

    }
    public void loadstatus(){

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

                                int schar_hr,schar_mint,actar_hr,actar_mint,latemint;
                                JSONObject status = object.getJSONObject("status");

                                CurrData = String.valueOf(responseCode);
//                                Toast.makeText(ShowLiveStatus.this,CurrData,Toast.LENGTH_LONG).show();

                                CurrData = "Sheduled Arrival = " + status.getString("scharr");
//                                char x,y;
//                                x=CurrData.charAt(0);
//                                y=CurrData.charAt(1);
//                                schar_hr = (x-'0')*10+(y-'0');
//                                x = CurrData.charAt(3);
//                                y = CurrData.charAt(4);
//                                schar_mint = (x-'0')*10+(y-'0');

                                Data.add(CurrData);

                                CurrData = "Sheduled Departure = " + status.getString("schdep");
                                Data.add(CurrData);
                                CurrData = "Sheduled Arrival Date = " + status.getString("scharr_date");
                                Data.add(CurrData);
                                flag = status.getBoolean("has_arrived");

                                if(flag)
                                {
                                    CurrData = "Train has already Arrived at this Station...";
                                }
                                else
                                {
                                    CurrData = "Train has NOT Arrived at this Station...";
                                }
                                Data.add(CurrData);


                                CurrData = "Actual Arrival = " + status.getString("actarr");
                                Data.add(CurrData);


//                                x=CurrData.charAt(0);
//                                y=CurrData.charAt(1);
//                                actar_hr = (x-'0')*10+(y-'0');
//                                x = CurrData.charAt(3);
//                                y = CurrData.charAt(4);
//                                actar_mint = (x-'0')*10+(y-'0');
//
//                                if(actar_mint>=schar_mint)
//                                {
//                                    latemint = actar_mint-schar_mint;
//                                }
//                                else
//                                {
//                                    actar_hr--;
//                                    latemint = 60+actar_mint-schar_mint;
//                                }
//
//                                if(actar_hr>=schar_hr)
//                                {
//                                    latemint += (actar_hr-schar_hr)*60;
//                                }
//                                else
//                                {
//                                    actar_hr+=12;
//                                    latemint += (actar_hr-schar_hr)*60;
//                                }



                                CurrData = "Actual Departure = " + status.getString("actdep");
                                Data.add(CurrData);
                                CurrData = "Actual Arrival Date = " + status.getString("actarr_date");
                                Data.add(CurrData);
                                flag = status.getBoolean("has_departed");

                                if(flag)
                                {
                                    CurrData = "Train has already Departed from this Station...";
                                }
                                else
                                {
                                    CurrData = "Train has NOT Departed from this Station...";
                                }
                                Data.add(CurrData);






                                int latemin= status.getInt("latemin");
                                int hr,mint;
                                hr = latemin/60;
                                mint = latemin%60;
                                String late;
                                if(hr==0)
                                {
                                    late = String.valueOf(mint)+" Minutes...";
                                }
                                else
                                {
                                    late = String.valueOf(hr)+" Hours " + String.valueOf(mint)+" Minutes...";
                                }
                                CurrData = "Late By = " + late;
                                Data.add(CurrData);

                                CurrData = "Current Status = " + object.getString("position");
                                Data.add(CurrData);
//

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowLiveStatus.this,android.R.layout.simple_list_item_1,android.R.id.text1,Data);

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


                        Toast.makeText(ShowLiveStatus.this,"Some problem has occurred with the server. Please try after some time.",Toast.LENGTH_LONG).show();


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(ShowLiveStatus.this);
        requestQueue.add(request);
    }
}
