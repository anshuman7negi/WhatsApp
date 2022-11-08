package com.example.whatsapp.tablayout.Fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.adapter.statusAdapter;


public class status extends Fragment {

    View view;
    RecyclerView recyclerView;
    ImageView statusImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_status, container, false);

        recyclerView=view.findViewById(R.id.statusRecyclerView);
        statusImage=view.findViewById(R.id.statusImage);

//        String names[]={"anshuman","ajay","kunal","himanshu"};
//        int images[]={R.drawable.anshuman,R.drawable.anshuman,R.drawable.anshuman,R.drawable.anshuman};
//
//        statusAdapter statusAdapter=new statusAdapter(images,names);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(statusAdapter);


        statusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Developer is lazy....still working on this feature", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}