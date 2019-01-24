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

public class PnrStatus extends AppCompatActivity {

    private ArrayList<String> Data;
    private String CurrData;
    private ListView list;
    private String PNR,URL,APIKEY,URL2;
    private EditText UserPnr;
    private Button CheckBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_status);
        APIKEY = "d9a868f6411e131a285d0df9b32f23ce";
        list= findViewById(R.id.StatusList);
        Data = new ArrayList<String>();

        UserPnr = findViewById(R.id.pnrnumber);


//        "https://api.railwayapi.com/v2/pnr-status/pnr/1234567890/apikey/myapikey/"

        CheckBtn = findViewById(R.id.getstatus);

        progressDialog = new ProgressDialog(this);


        CheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data.clear();
                PNR = UserPnr.getText().toString().trim();
                URL= "https://indianrailapi.com/api/v2/PNRCheck/apikey/d9a868f6411e131a285d0df9b32f23ce/PNRNumber/"+PNR+"/Route/1/";

                if(validatePNR(PNR))
                {


//                    URL2 = "https://indianrailapi.com/api/v2/PNRCheck/apikey/d9a868f6411e131a285d0df9b32f23ce/PNRNumber/6324655117/Route/1/";
                    if(isNetworkAvailable())
                        loadPnrStatus();
                    else
                        noNetwrokErrorMessage();
                }
                else
                {
                    Toast.makeText(PnrStatus.this,"Enter A Valid PNR Number",Toast.LENGTH_LONG).show();
//                    Toast.makeText(PnrStatus.this,PNR,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) PnrStatus.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage(){
        //

    }
    private Boolean validatePNR(String PNR)
    {
        if(PNR.length()!=10)
        {
            return false;
        }
        for(int i=0;i<10;i++)
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
            Toast.makeText(PnrStatus.this,"Invalid PNR Number",Toast.LENGTH_LONG).show();
        }
        else if(code==403){
            Toast.makeText(PnrStatus.this,"Request quota for the api is exceeded.",Toast.LENGTH_LONG).show();

        }
        else if(code==201){
            Toast.makeText(PnrStatus.this,"Entered PNR Number is NOT Correct...",Toast.LENGTH_LONG).show();

        }

        else
        {
            String res = String.valueOf(code);
            Toast.makeText(PnrStatus.this,"Something went wrong with the server. Please refresh after some time.",Toast.LENGTH_LONG).show();
        }

    }


    public void loadPnrStatus(){

        String url=URL;
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
//                        Log.e("response","hfhfhfgggg");
                        String trainNo="";
                        String trainName="";
                        String trainInfo="";
                        String source="";
                        String destination="";
                        String dateofjourney="";

                        int countPassenger=0;
                        String chartStatus="";
                        String classCode="";
                        ArrayList<String> status= new ArrayList<>();

                        progressDialog.cancel();
                        try {
                            JSONObject object= new JSONObject(response);
                            int responseCode=object.getInt("ResponseCode");
                            if(checkResponseCode(responseCode)){
                                trainNo=object.getString("TrainNumber");
                                CurrData = "Train Number = "+trainNo;
                                Data.add(CurrData);
                                trainName=object.getString("TrainName");
                                CurrData = "Train Name = "+trainName;
                                Data.add(CurrData);
                                trainInfo=trainName+"-"+trainNo;
                                dateofjourney=object.getString("JourneyDate");
                                CurrData = "Date Of Journey = "+dateofjourney;
                                Data.add(CurrData);

                                String name=object.getString("From");
                                CurrData = "From Station = "+name;
                                Data.add(CurrData);
                                name=object.getString("To");
                                CurrData = "To Station = "+name;
                                Data.add(CurrData);

                                String Chart = object.getString("ChatPrepared");
                                CurrData = "Chart Prepared = "+Chart;
                                Data.add(CurrData);
                                CurrData = "Passengers:";
                                Data.add("  ");
                                String idx;
                                JSONArray pass = object.getJSONArray("Passangers");
                                for(int i=0;i<pass.length();i++)
                                {
                                    idx = String.valueOf(i+1);
                                    CurrData = idx+". "+"BookingStatus "+pass.getJSONObject(i).getString("BookingStatus")+" CurrentStatus "+pass.getJSONObject(i).getString("CurrentStatus");
                                    Data.add(CurrData);
                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PnrStatus.this,android.R.layout.simple_list_item_1,android.R.id.text1,Data);

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


                        Toast.makeText(PnrStatus.this,"Some problem has occurred with the server. Please try after some time.",Toast.LENGTH_LONG).show();


                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(PnrStatus.this);
        requestQueue.add(request);
    }
}
