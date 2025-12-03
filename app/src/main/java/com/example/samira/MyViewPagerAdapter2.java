package com.example.samira;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.samira.AjouterTFragment;
import com.example.samira.EnregistresTFragment;
public class MyViewPagerAdapter2  extends FragmentStateAdapter {
    public MyViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new EnregistresTFragment();

            default:
                return new AjouterTFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}