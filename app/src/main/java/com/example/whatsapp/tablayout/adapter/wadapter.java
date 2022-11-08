package com.example.whatsapp.tablayout.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whatsapp.tablayout.Fragments.call;
import com.example.whatsapp.tablayout.Fragments.chats;
import com.example.whatsapp.tablayout.Fragments.status;

public class wadapter extends FragmentStateAdapter
{
    public wadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle)
    {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return  new status();
            case 2:
                return new call();

            default:
                return new chats();
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
