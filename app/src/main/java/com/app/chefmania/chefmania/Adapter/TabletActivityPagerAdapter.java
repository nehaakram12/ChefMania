package com.app.chefmania.chefmania.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.chefmania.chefmania.Fragment.TabletAddNewFragment;
import com.app.chefmania.chefmania.Fragment.TabletEditFragment;
import com.app.chefmania.chefmania.Fragment.TabletViewAllFragment;

public class TabletActivityPagerAdapter extends FragmentPagerAdapter {

    private int nooftabs;
    private Fragment home = new TabletViewAllFragment();
    private Fragment upload = new TabletAddNewFragment();
    private Fragment profile = new TabletEditFragment();

    public TabletActivityPagerAdapter(FragmentManager fm, int NumberOfTabs) {
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


