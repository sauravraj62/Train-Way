package com.example.trainway.trainway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private TextView tvName,tvEmail,tvPass;
    private Button Register;
    private EditText etName,etPass,etEmail;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        tvName= (TextView) findViewById(R.id.UserName);
        tvEmail= (TextView) findViewById(R.id.UserEMail);
        tvPass= (TextView) findViewById(R.id.UserPasword);

        tvName.setText("Enter Your Name:");
        tvEmail.setText("Enter Your Email:");
        tvPass.setText(("Enter Your Password:"));

        etName = findViewById(R.id.et2Name);
        etEmail = findViewById(R.id.et2Email);
        etPass = findViewById(R.id.et2Password);

        Register = findViewById(R.id.signupbtn);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Registering You In...");
                progressDialog.show();
                if(validate())
                {
                    String Email,Pass;
                    Email = etEmail.getText().toString().trim();
                    Pass = etPass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Register.this,MainActivity.class));
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Register.this,"Enter Valid Details",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private Boolean validate()
    {
        String Name,Pass,Email;
        Name = etName.getText().toString();

        Pass = etPass.getText().toString();

        Email = etEmail.getText().toString();

        return (!Name.isEmpty() && !Email.isEmpty() && !Pass.isEmpty());

    }
}
