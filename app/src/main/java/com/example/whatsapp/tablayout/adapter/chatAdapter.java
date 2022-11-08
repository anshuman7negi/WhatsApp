package com.example.whatsapp.tablayout.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.ChatContainer.ChatsPage;
import com.example.whatsapp.tablayout.profile.profileDataHolder;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MyViewHolder>
{
    Context context;
    ArrayList<profileDataHolder> list;

    public chatAdapter(Context context, ArrayList<profileDataHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public chatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.chatslist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chatAdapter.MyViewHolder holder, int position) {
        profileDataHolder dataHolder=list.get(position);
        holder.text.setText(dataHolder.getName());
        Picasso.get().load(dataHolder.getProfilePic()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatsPage.class);
                intent.putExtra("name",dataHolder.getName());
                intent.putExtra("RecieverImage",dataHolder.getProfilePic());
                intent.putExtra("uid",dataHolder.getUid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView text;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            img=itemView.findViewById(R.id.profile);
            text=itemView.findViewById(R.id.name);
        }
    }

}
