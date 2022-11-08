package com.example.whatsapp.tablayout.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.adapter.CallAdapter;

import java.text.BreakIterator;


public class call extends Fragment {

    View view;
    RecyclerView recyclerView;
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_call, container, false);
        recyclerView=view.findViewById(R.id.callRecyclerView);
        cardView=view.findViewById(R.id.callCardView);

        String names[]={"anshuman"};
        int images[]={R.drawable.anshuman};


        CallAdapter callAdapter=new CallAdapter(images,names);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(callAdapter);

        return view;
    }

}