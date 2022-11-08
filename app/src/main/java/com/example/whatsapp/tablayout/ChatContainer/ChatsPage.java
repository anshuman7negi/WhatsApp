package com.example.whatsapp.tablayout.ChatContainer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.adapter.MessageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatsPage extends AppCompatActivity {

    String RecieverName,RecieverImage,RecieverUid,SenderUid;
    ImageView chatImage;
    TextView chatName;
    EditText editMessage;
    CardView sendBtn;
    RecyclerView messageRecyclerView;

    String senderRoom,RecieverRoom;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    public static String senderImage;
    public static String recieverImage;

    ArrayList<Messages> messagesArrayList;

    MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_page);

        getSupportActionBar().hide();

        messagesArrayList=new ArrayList<>();

        RecieverName=getIntent().getStringExtra("name");
        RecieverImage=getIntent().getStringExtra("RecieverImage");
        RecieverUid=getIntent().getStringExtra("uid");

        chatImage=findViewById(R.id.chatImage);
        chatName=findViewById(R.id.chatName);
        editMessage=findViewById(R.id.editMessage);
        sendBtn=findViewById(R.id.sendBtn);

        messageRecyclerView=findViewById(R.id.messageRecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter=new MessageAdapter(ChatsPage.this,messagesArrayList);
        messageRecyclerView.setAdapter(messageAdapter);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        SenderUid=auth.getCurrentUser().getUid();
        chatName.setText(RecieverName);
        Picasso.get().load(RecieverImage).into(chatImage);

        senderRoom=SenderUid+RecieverUid;
        RecieverRoom=RecieverUid+SenderUid;

        DatabaseReference chatReference=database.getReference().child("Chats").child(senderRoom).child("Messages");


        reference.child("Users").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       senderImage= snapshot.child("profilePic").getValue().toString();
                       recieverImage=RecieverImage;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message=editMessage.getText().toString();
                editMessage.setText("");
                Date date=new Date();

                if(message.isEmpty())
                {
                    Toast.makeText(ChatsPage.this, "Invalid Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                Messages messages=new Messages(message,SenderUid,date.getTime());

                reference.child("Chats")
                        .child(senderRoom)
                        .child("Messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                reference.child("Chats")
                                        .child(RecieverRoom)
                                        .child("Messages")
                                        .push()
                                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                            }
                        });

            }
        });


        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesArrayList.clear();

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Messages messages=dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                   messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}