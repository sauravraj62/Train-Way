package com.example.trainway.trainway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FareQueryDetails extends AppCompatActivity {

    private EditText et1,et2,et3;
    private Button Check;
    private String tno,scode,dcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_query_details);
        initialize();


        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tno = et1.getText().toString();
                scode = et2.getText().toString();
                dcode = et3.getText().toString();
                if(validate(tno,scode,dcode))
                {
                    Intent intent = new Intent(FareQueryDetails.this,FareQuery.class);
                    intent.putExtra("tno",tno);
                    intent.putExtra("scode",scode);
                    intent.putExtra("dcode",dcode);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(FareQueryDetails.this,"Enter Valid Details...",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private Boolean validate(String str1,String str2,String str3)
    {
        if(str1.length()==0 || str2.length()==0 || str3.length()==0)
            return false;

        if(str1.length()!=5)
            return  false;

        char z;

        for(int i=0;i<5;i++)
        {
            z = str1.charAt(i);
            if(!(z>='0' && z<='9'))
                return false;
        }
        for(int i=0;i<str2.length();i++)
        {
            z = str2.charAt(i);
            if(!(z>='a' && z<='z') && !(z>='A' && z<='Z'))
                return false;
        }
        for(int i=0;i<str3.length();i++)
        {
            z = str3.charAt(i);
            if(!(z>='a' && z<='z') && !(z>='A' && z<='Z'))
                return false;
        }


        return true;
    }

    private void initialize()
    {
        et1 = findViewById(R.id.fareedit11);
        et2 = findViewById(R.id.fareedit22);
        et3 = findViewById(R.id.fareedit33);
        Check = findViewById(R.id.farecheckbtn1);
    }

}
