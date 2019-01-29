package com.example.trainway.trainway;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoard extends AppCompatActivity {


    private Button Logout,pnrbtn,routebtn,trnbtw,farebtn,trainsatstationbtn,livestatusbtn,check;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();



        final FirebaseUser usr = firebaseAuth.getCurrentUser();




        Logout = findViewById(R.id.logoutbtn);
        pnrbtn = findViewById(R.id.pnrbtn);
        routebtn = findViewById(R.id.trainroutebtn);
        trnbtw = findViewById(R.id.trainbtwstationsbtn);
        farebtn = findViewById(R.id.farebtn);
        trainsatstationbtn = findViewById(R.id.trainsatstation);
        livestatusbtn = findViewById(R.id.livestatusbtn);
        check = findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,DashBoard_New.class));
            }
        });

        livestatusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,LiveStatus.class));
            }
        });


        if((usr==null))
        {
            Logout.setText("Login Here");
        }

        trainsatstationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,TrainsAtStation.class));
            }
        });

        farebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,FareQueryDetails.class));
            }
        });

        trnbtw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,TrainBtwStations.class));
            }
        });

        routebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,TrainRoute.class));
            }
        });

        pnrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,Pnr_Details_Entry.class));
            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usr==null)
                {
                    finish();
                    startActivity(new Intent(DashBoard.this,MainActivity.class));
                }
                else
                {
                    progressDialog.setMessage("Logging You Out!!!");
                    firebaseAuth.signOut();
                    progressDialog.dismiss();
                    Toast.makeText(DashBoard.this,"Logout Successful",Toast.LENGTH_LONG);
                    finish();
                    startActivity(new Intent(DashBoard.this,MainActivity.class));
                }

            }
        });
    }
}
