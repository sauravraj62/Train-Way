package com.example.trainway.trainway;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainRouteDetails extends AppCompatActivity {

    private EditText trainNo;
    private String trNO;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_route_details);

        trainNo = findViewById(R.id.trainnumberdetials);
        btn = findViewById(R.id.getRoute1);


//        trNO = "13009";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trNO = trainNo.getText().toString();
                if(valid(trNO))
                {
                    Intent intent = new Intent(TrainRouteDetails.this,TrainRoute.class);
                    intent.putExtra("tno",trNO);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(TrainRouteDetails.this,trNO,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Boolean valid(String str)
    {
        if(str.length()!=5)
            return false;
        for(int i=0;i<5;i++)
        {
            char z = str.charAt(i);
            if(!(z>='0' && z<='9'))
                return false;
        }
        return true;
    }
}
