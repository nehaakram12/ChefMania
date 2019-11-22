package com.app.chefmania.chefmania.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.chefmania.chefmania.Fragment.FoodMenuAddNewFragment;
import com.app.chefmania.chefmania.Fragment.FoodMenuEditFragment;
import com.app.chefmania.chefmania.Fragment.FoodMenuViewAllFragment;

public class FoodMenuActivityPagerAdapter extends FragmentPagerAdapter {

    private int nooftabs;
    private Fragment home = new FoodMenuViewAllFragment();
    private Fragment upload = new FoodMenuAddNewFragment();
    private Fragment profile = new FoodMenuEditFragment();

    public FoodMenuActivityPagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        nooftabs = NumberOfTabs;
    }

    @Override
    public  Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return home;
            case 1:
                return upload;
            case 2:
                return profile;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nooftabs;
    }
}


