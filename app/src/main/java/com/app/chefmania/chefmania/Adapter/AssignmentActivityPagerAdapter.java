package com.app.chefmania.chefmania.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.chefmania.chefmania.Fragment.AssignmentAddNewFragment;
import com.app.chefmania.chefmania.Fragment.AssignmentEditFragment;
import com.app.chefmania.chefmania.Fragment.AssignmentViewAllFragment;

public class AssignmentActivityPagerAdapter extends FragmentPagerAdapter {

    private int nooftabs;
    private Fragment home = new AssignmentViewAllFragment();
    private Fragment upload = new AssignmentAddNewFragment();
    private Fragment profile = new AssignmentEditFragment();

    public AssignmentActivityPagerAdapter(FragmentManager fm, int NumberOfTabs) {
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


