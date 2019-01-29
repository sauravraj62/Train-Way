package com.example.trainway.trainway;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LiveStatus extends AppCompatActivity {
    private EditText trnno,stncode,datetxt;
    private String trainno,stationcode,date;
    private Button Check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_status);

        getaction();

        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainno = trnno.getText().toString();
                stationcode = stncode.getText().toString();
                date = datetxt.getText().toString();
                stationcode.toUpperCase();
                Intent intent =new  Intent(LiveStatus.this,ShowLiveStatus.class);
                intent.putExtra("trainno",trainno);
                intent.putExtra("stationcode",stationcode);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
    }
    private void getaction()
    {
        trnno = findViewById(R.id.livestatustrainid);
        stncode = findViewById(R.id.livestatusstationcodeid);
        datetxt = findViewById(R.id.dateid);
        Check = findViewById(R.id.livestatusgetbtn);
    }


}
