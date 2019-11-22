package com.app.chefmania.chefmania.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.chefmania.chefmania.Fragment.InventoryAddNewFragment;
import com.app.chefmania.chefmania.Fragment.InventoryEditFragment;
import com.app.chefmania.chefmania.Fragment.InventoryViewAllFragment;

public class InventoryActivityPagerAdapter extends FragmentPagerAdapter {

    private int nooftabs;
    private Fragment home = new InventoryViewAllFragment();
    private Fragment upload = new InventoryAddNewFragment();
    private Fragment profile = new InventoryEditFragment();

    public InventoryActivityPagerAdapter(FragmentManager fm, int NumberOfTabs) {
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


