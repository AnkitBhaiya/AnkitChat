package com.ankitsharma.ankitchat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ankitsharma.ankitchat.Models.MessagesModel;
import com.ankitsharma.ankitchat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessagesModel> messagesModels;
    Context context;

    int SENDER_VIEW_TYPE = 100;
    int RECIEVER_VIEW_TYPE = 200;

    public ChatAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels=messagesModels;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE){
            View view =LayoutInflater.from (context).inflate (R.layout.sample_sender,parent,false);
            return new SenderViewHolder (view);
        }
     else {
            View view =LayoutInflater.from (context).inflate (R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder (view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (messagesModels.get(position).getuId ().equals (FirebaseAuth.getInstance ().getUid ())){
            return SENDER_VIEW_TYPE;
        }

        else {
            return RECIEVER_VIEW_TYPE;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessagesModel messagesModel = messagesModels.get(position);
        if(holder.getClass () == SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMessage.setText (messagesModel.getMessage ());
        }
       else {
            ((RecieverViewHolder)holder).recieverMessage.setText (messagesModel.getMessage ());
        }
    }

    @Override
    public int getItemCount() {
        return messagesModels.size ();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView recieverMessage,recieverTime;


        public RecieverViewHolder(@NonNull View itemView) {
            super (itemView);
            recieverMessage = itemView.findViewById (R.id.recievertext);
            recieverTime = itemView.findViewById (R.id.recievertime);
        }
    }

    public  class SenderViewHolder extends RecyclerView.ViewHolder{
       TextView senderMessage,senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super (itemView);
            senderMessage = itemView.findViewById (R.id.sendertext);
            senderTime = itemView.findViewById (R.id.sendertime);

        }
    }

}
