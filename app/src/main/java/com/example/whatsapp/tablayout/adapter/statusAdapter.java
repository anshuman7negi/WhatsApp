package com.example.whatsapp.tablayout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;

public class statusAdapter extends RecyclerView.Adapter<statusAdapter.MyViewHolder> {

    int images[];
    String names[];

    public statusAdapter(int images[] ,String names[]) {
        this.images=images;
        this.names=names;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.status,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          holder.images.setImageResource(images[position]);
          holder.textView.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.statusPic);
            textView=itemView.findViewById(R.id.statusName);
        }
    }
}
