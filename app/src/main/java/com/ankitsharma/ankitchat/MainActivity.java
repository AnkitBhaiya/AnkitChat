package com.ankitsharma.ankitchat;

import static com.ankitsharma.ankitchat.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ankitsharma.ankitchat.Adapters.FragmentAdapter;
import com.ankitsharma.ankitchat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding =ActivityMainBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        DatabaseReference myRef =database.getReference ("message");
        myRef.setValue ("Hello World!");

        auth = FirebaseAuth.getInstance ();
        binding.viewpager.setAdapter (new FragmentAdapter (getSupportFragmentManager ()));
        binding.tabLayout.setupWithViewPager(binding.viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.menu,menu);

        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId ()){
            case id.settings:
                break;
            case id.logout:
            auth.signOut ();
            Intent intent = new Intent (MainActivity.this,LoginActivity.class);
            startActivity (intent);
            break;
            case id.groups:
               Intent intent1=new Intent (MainActivity.this, GroupChatActivity.class);
                startActivity (intent1);
                break;

        }

        return true;
    }

    }
