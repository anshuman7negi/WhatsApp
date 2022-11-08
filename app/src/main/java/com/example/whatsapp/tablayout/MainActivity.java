package com.example.whatsapp.tablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.tablayout.Fragments.call;
import com.example.whatsapp.tablayout.Fragments.chats;
import com.example.whatsapp.tablayout.Fragments.status;
import com.example.whatsapp.tablayout.Setting.ProfileSetting;
import com.example.whatsapp.tablayout.adapter.chatAdapter;
import com.example.whatsapp.tablayout.adapter.wadapter;
import com.example.whatsapp.tablayout.otpLogin.OtpSend;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private  boolean doublebackPress=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);


        wadapter wadapter=new wadapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(wadapter);


          tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
              @Override
              public void onTabSelected(TabLayout.Tab tab) {

                 viewPager.setCurrentItem(tab.getPosition());
              }

              @Override
              public void onTabUnselected(TabLayout.Tab tab) {

              }

              @Override
              public void onTabReselected(TabLayout.Tab tab) {

              }
          });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int item_id=item.getItemId();

        switch (item_id)
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, OtpSend.class));
                finish();
                return true;
            case R.id.setting:
                startActivity(new Intent(MainActivity.this, ProfileSetting.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        if(doublebackPress)
        {
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
        doublebackPress=true;
    }
}