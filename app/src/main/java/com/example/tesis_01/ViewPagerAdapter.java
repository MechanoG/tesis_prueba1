package com.example.tesis_01;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new Fragment_info_productos();

            case 1:
                return new  Fragment_info_empleados();

            case 2:
                return new Fragment_info_clientes();

            default:
                return new Fragment_info_productos();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
