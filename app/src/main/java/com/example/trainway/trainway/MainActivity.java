package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView tx1,tx2,tx3;
    private Button btn1,skipbtn;
    private int count=5;
    private String Email,Pass;
    private EditText UserName,Password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser usr = firebaseAuth.getCurrentUser();
        if(usr!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, DashBoard.class));
        }

        progressDialog = new ProgressDialog(this);


        tx1= findViewById(R.id.welcomtxt);
        tx2= findViewById((R.id.validtimes));
        tx3= findViewById(R.id.signuptxt);


        tx2.setText("Remaining Valid Chances:"+count);

        btn1= findViewById(R.id.signbtn);
        skipbtn = findViewById(R.id.skipbtn);
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DashBoard_New.class));
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserName = findViewById(R.id.etUserMail1) ;
                Password = findViewById(R.id.etUserPassword1);

                Email = UserName.getText().toString();
                Pass = Password.getText().toString();

                if(Email.length()==0)
                {
                    Toast.makeText(MainActivity.this,"Enter A Valid Email",Toast.LENGTH_LONG).show();
                }
                else if(Pass.length()==0)
                {
                    Toast.makeText(MainActivity.this,"Enter A Valid PassWord",Toast.LENGTH_LONG).show();
                }
                else
                validate(Email,Pass);
            }
        });


        tx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent it = new Intent(MainActivity.this,Register.class);
                    startActivity(it);
                }
        });
    }
    private void validate(String Email,String Pass)
    {
        progressDialog.setMessage("Logging You In");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,DashBoard_New.class));
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                    count--;
                    tx2.setText("Remaining Valid Chances:"+count);
                    if(count==0)
                    {
                        btn1.setEnabled(false);
                        tx2.setText("Too Many Wrong Attempts!!!");
                    }
                }
            }
        });
    }

}
