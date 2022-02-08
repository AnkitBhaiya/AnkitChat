package com.ankitsharma.ankitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ankitsharma.ankitchat.Models.Users;
import com.ankitsharma.ankitchat.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivitySignUpBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());
        getSupportActionBar ().hide ();
        auth = FirebaseAuth.getInstance ();
        database = FirebaseDatabase.getInstance ();

        progressDialog = new ProgressDialog (SignUpActivity.this);
        progressDialog.setTitle ("Creating your account");
        progressDialog.setMessage ("We are creating your account");

        binding.Signup.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                progressDialog.show ();
            auth.createUserWithEmailAndPassword (binding.email.getText ().toString (),binding.password.getText ().toString ()).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                 progressDialog.dismiss ();
                    if (task.isSuccessful ()){
                        Users users = new Users (binding.name.getText ().toString (),binding.email.getText ().toString (),binding.password.getText ().toString ());
                       String id = task.getResult ().getUser ().getUid ();

                       database.getReference ().child ("Users").child (id).setValue (users);
                        Toast.makeText (SignUpActivity.this,"User Created Successfully",Toast.LENGTH_SHORT).show ();
                    }

                    else {
                        Toast.makeText (SignUpActivity.this,task.getException ().getMessage (),Toast.LENGTH_SHORT).show ();
                    }

                }
            });
            }
        });
        binding.login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SignUpActivity.this,LoginActivity.class);
                startActivity (intent);
            }
        });
    }
}