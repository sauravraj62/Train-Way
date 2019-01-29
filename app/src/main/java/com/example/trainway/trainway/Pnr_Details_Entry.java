package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
public class Pnr_Details_Entry extends AppCompatActivity {
    private String CurrData;
    private ListView list;
    private String PNR,URL,APIKEY,URL2;
    private EditText UserPnr;
    private Button CheckBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr__details__entry);
        APIKEY = "d9a868f6411e131a285d0df9b32f23ce";

        UserPnr = findViewById(R.id.pnrnumber1);


//        "https://api.railwayapi.com/v2/pnr-status/pnr/1234567890/apikey/myapikey/"

        CheckBtn = findViewById(R.id.getstatus1);

        progressDialog = new ProgressDialog(this);


        CheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PNR = UserPnr.getText().toString().trim();
                URL= "https://indianrailapi.com/api/v2/PNRCheck/apikey/d9a868f6411e131a285d0df9b32f23ce/PNRNumber/"+PNR+"/Route/1/";

                if(validatePNR(PNR))
                {
                    Intent intent = new Intent(Pnr_Details_Entry.this,PnrStatus.class);
                    intent.putExtra("pnr",PNR);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Pnr_Details_Entry.this,"Enter A Valid PNR Number",Toast.LENGTH_LONG).show();
//                    Toast.makeText(PnrStatus.this,PNR,Toast.LENGTH_LONG).show();
                }
            }
        });

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
}
