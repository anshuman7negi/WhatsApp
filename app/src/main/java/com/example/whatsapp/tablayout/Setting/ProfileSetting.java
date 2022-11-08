package com.example.whatsapp.tablayout.Setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.MainActivity;
import com.example.whatsapp.tablayout.profile.SelectProfile;
import com.example.whatsapp.tablayout.profile.profileDataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class ProfileSetting extends AppCompatActivity {

    ImageView settingImage;
    EditText settingName,settingStatus;
    Button settingBtn;
    ProgressBar settingProgressBar;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        settingImage=findViewById(R.id.settingImage);
        settingName=findViewById(R.id.settingName);
        settingStatus=findViewById(R.id.settingStatus);
        settingBtn=findViewById(R.id.settingBtn);
        settingProgressBar=findViewById(R.id.settingProgressBar);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();

        DatabaseReference databaseReference=database.getReference().child("Users").child(auth.getCurrentUser().getUid());
        StorageReference storageReference=storage.getReference().child("ProfilePic").child(auth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("name").getValue().toString();
                String profile=snapshot.child("profilePic").getValue().toString();

                settingName.setText(name);
                Picasso.get().load(profile).into(settingImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(ProfileSetting.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"select Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });



        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingBtn.setVisibility(View.GONE);
                settingProgressBar.setVisibility(View.VISIBLE);
               String name= settingName.getText().toString();
                if(uri != null)
                {
                    storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                              storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                  @Override
                                  public void onSuccess(Uri uri) {
                                      String finalImageUri=uri.toString();
                                      profileDataHolder dataHolder=new profileDataHolder(name,finalImageUri,auth.getCurrentUser().getUid());

                                      databaseReference.setValue(dataHolder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull Task<Void> task) {
                                              if(task.isSuccessful())
                                              {
                                                  settingProgressBar.setVisibility(View.GONE);
                                                  settingBtn.setVisibility(View.VISIBLE);
                                                  Toast.makeText(ProfileSetting.this, "Data Saved", Toast.LENGTH_SHORT).show();
                                                  startActivity(new Intent(ProfileSetting.this, MainActivity.class));
                                                  finish();
                                              }
                                              else
                                              {
                                                  settingProgressBar.setVisibility(View.GONE);
                                                  settingBtn.setVisibility(View.VISIBLE);
                                                  Toast.makeText(ProfileSetting.this, "error", Toast.LENGTH_SHORT).show();
                                                  startActivity(new Intent(ProfileSetting.this, MainActivity.class));
                                                  finish();
                                              }
                                          }
                                      });
                                  }
                              });
                        }
                    });
                }else{
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String finalImageUri=uri.toString();
                            profileDataHolder dataHolder=new profileDataHolder(name,finalImageUri,auth.getCurrentUser().getUid());

                            databaseReference.setValue(dataHolder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        settingProgressBar.setVisibility(View.GONE);
                                        settingBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(ProfileSetting.this, "Data Saved", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProfileSetting.this, MainActivity.class));
                                        finish();

                                    }
                                    else
                                    {
                                        settingProgressBar.setVisibility(View.GONE);
                                        settingBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(ProfileSetting.this, "error", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProfileSetting.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                        }
                    });
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==1)
        {
            uri=data.getData();

            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                settingImage.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "error:"+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}