package com.example.trainway.trainway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainBtwStations extends AppCompatActivity {

    private EditText src,dest,datetxt;
    private Button check;
    String source,destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_btw_stations);
        src = findViewById(R.id.sourcecode);
        dest = findViewById(R.id.destinationcode);

        check = findViewById(R.id.checkbtn);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source = src.getText().toString();
                destination = dest.getText().toString();
                if(validate(source,destination))
                {
                    Intent intent = new Intent(TrainBtwStations.this,ShowTrains.class);
                    intent.putExtra("src",source);
                    intent.putExtra("dest",destination);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(TrainBtwStations.this,"Enter A Valid Station Code!!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private Boolean validate(String src,String dest)
    {
        Boolean flag = true;

        if(src.length()==0 || dest.length()==0)
            flag=false;

        return flag;
    }
}
