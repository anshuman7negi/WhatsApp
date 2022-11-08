package com.example.whatsapp.tablayout.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.MyViewHolder> {

    int images[];
    String text[];
    String number="918979697532";
    int REQUEST_CALL_CODE=1;
    public CallAdapter(int images[],String text[])
    {
        this.images=images;
        this.text=text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.call,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
             holder.image.setImageResource(images[position]);
             holder.textView.setText(text[position]);
             holder.cardview.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Intent intent=new Intent(Intent.ACTION_DIAL);
                     intent.setData(Uri.parse("tel:"+number));
                     view.getContext().startActivity(intent);
                 }


             });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textView;
        CardView cardview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.callImage);
            textView=itemView.findViewById(R.id.callName);
            cardview = itemView.findViewById(R.id.callCardView);
        }
    }


}
