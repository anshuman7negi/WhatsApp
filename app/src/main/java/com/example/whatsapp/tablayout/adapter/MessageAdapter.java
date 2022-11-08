package com.example.whatsapp.tablayout.adapter;



import static com.example.whatsapp.tablayout.ChatContainer.ChatsPage.recieverImage;
import static com.example.whatsapp.tablayout.ChatContainer.ChatsPage.senderImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.ChatContainer.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter {


    Context context;
    ArrayList<Messages> messagesArrayList;
    int ITEM_SEND=1;
    int ITEM_Recieve=2;

    public MessageAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.reciever_layout,parent,false);
            return new RecyclerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages=messagesArrayList.get(position);

        if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder) holder;
            viewHolder.senderText.setText(messages.getMessage());
            Picasso.get().load(senderImage).into(viewHolder.senderChatImage);
        }else{

            RecyclerViewHolder viewHolder=(RecyclerViewHolder) holder;
            viewHolder.reciverText.setText(messages.getMessage());
            Picasso.get().load(recieverImage).into(viewHolder.reciverChatImage);
        }

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Messages messages=messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderId()))
        {
            return ITEM_SEND;
        }
        else
        {
            return ITEM_Recieve;
        }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        ImageView senderChatImage;
        TextView senderText;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderChatImage=itemView.findViewById(R.id.senderChatImage);
            senderText=itemView.findViewById(R.id.senderText);
        }
    }

    class RecyclerViewHolder extends  RecyclerView.ViewHolder{
        ImageView reciverChatImage;
        TextView reciverText;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            reciverChatImage=itemView.findViewById(R.id.reciverChatImage);
            reciverText=itemView.findViewById(R.id.reciverText);
        }
    }

}
