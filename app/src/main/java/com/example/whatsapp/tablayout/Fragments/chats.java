package com.example.whatsapp.tablayout.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.adapter.chatAdapter;
import com.example.whatsapp.tablayout.profile.profileDataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class chats extends Fragment {

   View view;
   FirebaseAuth auth;
   FirebaseDatabase database;
   ArrayList<profileDataHolder> user;
   RecyclerView recyclerView;
   DatabaseReference reference;
   chatAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_chats, container, false);


        final FragmentActivity c=getActivity();
       auth=FirebaseAuth.getInstance();
       database=FirebaseDatabase.getInstance();
       reference=database.getReference();
       recyclerView=view.findViewById(R.id.recycleView_One);

       user=new ArrayList<>();

       reference.child("Users").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for(DataSnapshot dataSnapshot:snapshot.getChildren())
               {
                   profileDataHolder data=dataSnapshot.getValue(profileDataHolder.class);
                   data.setUid(dataSnapshot.getKey());
                   if(!data.getUid().equals(auth.getCurrentUser().getUid()))
                   {
                       user.add(data);
                   }
               }
               adapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        adapter=new chatAdapter(getActivity(),user);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;

    }


}