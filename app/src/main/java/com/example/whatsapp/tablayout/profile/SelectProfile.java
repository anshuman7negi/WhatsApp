package com.example.whatsapp.tablayout.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.ArrayList;

public class SelectProfile extends AppCompatActivity {

    FirebaseDatabase database ;
    DatabaseReference myRef;
    FirebaseAuth auth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    ImageView selectProfileImage;
    EditText nameSelectProfile,aboutMe;
    ProgressBar selectProfileProgress;
    Button saveSelectProfile;
    Uri uri;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        selectProfileImage=findViewById(R.id.selectProfileImage);
        nameSelectProfile=findViewById(R.id.nameSelectProfile);
        aboutMe=findViewById(R.id.aboutMe);
        saveSelectProfile=findViewById(R.id.saveSelectProfile);
        selectProfileProgress=findViewById(R.id.selectProfileProgress);


         database = FirebaseDatabase.getInstance();
         auth=FirebaseAuth.getInstance();
        myRef = database.getReference().child("Users").child(auth.getCurrentUser().getUid());
        firebaseStorage=FirebaseStorage.getInstance();
         storageReference=firebaseStorage.getReference().child("ProfilePic").child(auth.getCurrentUser().getUid());


         selectProfileImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Dexter.withActivity(SelectProfile.this)
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


        saveSelectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names=nameSelectProfile.getText().toString().trim();
                if(TextUtils.isEmpty(names) || names.length()>20)
                {
                    Toast.makeText(SelectProfile.this, "Name should be correct", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    saveSelectProfile.setVisibility(View.GONE);
                    selectProfileProgress.setVisibility(View.VISIBLE);
                    saveProfileData();
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
                selectProfileImage.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "error:"+e, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private  void saveProfileData()
    {
        String name=nameSelectProfile.getText().toString().trim();

        if(uri!=null)
        {
            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                           if(task.isSuccessful())
                           {
                               selectProfileProgress.setVisibility(View.GONE);
                               saveSelectProfile.setVisibility(View.VISIBLE);

                               storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       imageUri=uri.toString();
                                       profileDataHolder dataHolder=new profileDataHolder(name,imageUri,auth.getCurrentUser().getUid());
                                       
                                       myRef.setValue(dataHolder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful())
                                               {
                                                   Toast.makeText(SelectProfile.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                   startActivity(new Intent(SelectProfile.this,MainActivity.class));
                                                   finish();
                                               }
                                               else
                                               {
                                                   selectProfileProgress.setVisibility(View.GONE);
                                                   saveSelectProfile.setVisibility(View.VISIBLE);
                                                   Toast.makeText(SelectProfile.this, "Error", Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       });
                                   }
                               });
                           }
                }
            });
        } else
        {
            imageUri="https://firebasestorage.googleapis.com/v0/b/whatsapp-e79e0.appspot.com/o/anshuman.png?alt=media&token=fba4f92c-dff0-4f57-84ed-6c265be1391f";
            profileDataHolder dataHolder=new profileDataHolder(name,imageUri,auth.getCurrentUser().getUid());

            myRef.setValue(dataHolder).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SelectProfile.this, "Data saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SelectProfile.this,MainActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SelectProfile.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}