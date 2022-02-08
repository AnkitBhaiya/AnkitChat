package com.ankitsharma.ankitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ankitsharma.ankitchat.Adapters.ChatAdapter;
import com.ankitsharma.ankitchat.Models.MessagesModel;
import com.ankitsharma.ankitchat.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityChatDetailBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());
       getSupportActionBar ().hide ();
        database = FirebaseDatabase.getInstance ();
        auth = FirebaseAuth.getInstance ();
        final String senderId =auth.getUid ();
        String recieveId = getIntent ().getStringExtra ("userId");
        String userName = getIntent ().getStringExtra ("userName");
        String profilePic = getIntent ().getStringExtra ("profilePic");

        binding.username.setText (userName);
        Picasso.get ().load (profilePic).placeholder (R.drawable.avatar).into (binding.profileImage);
       binding.backarrow.setOnClickListener (new View.OnClickListener () {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent (ChatDetailActivity.this,MainActivity.class);
               startActivity (intent);
           }
       });

       final ArrayList<MessagesModel> messagesModels = new ArrayList<> ();
       final ChatAdapter chatAdapter = new ChatAdapter (messagesModels,this);
       binding.recyclerView.setAdapter (chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager (this);
        binding.recyclerView.setLayoutManager (layoutManager);

        final String senderRoom = senderId + recieveId;
        final String recieverRoom = recieveId + senderId;

        database.getReference ().child ("chats")
                .child (senderRoom)
                .addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesModels.clear ();
                for(DataSnapshot snapshot1 : snapshot.getChildren ()){
                    MessagesModel model = snapshot1.getValue (MessagesModel.class);
                    messagesModels.add (model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
             String message =   binding.etMessage.getText ().toString ();
             final MessagesModel model = new MessagesModel (senderId,message);
             model.setTimestamp (new Date ().getTime ());
             binding.etMessage.setText ("");

             database.getReference ().child ("chats").child (senderRoom).push ().setValue (model).addOnSuccessListener (new OnSuccessListener<Void> () {
                 @Override
                 public void onSuccess(Void unused) {
                     database.getReference ().child ("chats").child (recieverRoom).push ().setValue (model).addOnSuccessListener (new OnSuccessListener<Void> () {
                         @Override
                         public void onSuccess(Void unused) {

                         }
                     });
                 }
             });
            }
        });

    }
}