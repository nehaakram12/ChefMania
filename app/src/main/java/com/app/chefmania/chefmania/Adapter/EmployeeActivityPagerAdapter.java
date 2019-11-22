package com.app.chefmania.chefmania.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.chefmania.chefmania.Fragment.EmployeeAddNewFragment;
import com.app.chefmania.chefmania.Fragment.EmployeeEditFragment;
import com.app.chefmania.chefmania.Fragment.EmployeeViewAllFragment;

public class EmployeeActivityPagerAdapter extends FragmentPagerAdapter {

    private int nooftabs;
    private Fragment home = new EmployeeViewAllFragment();
    private Fragment upload = new EmployeeAddNewFragment();
    private Fragment profile = new EmployeeEditFragment();

    public EmployeeActivityPagerAdapter(FragmentManager fm, int NumberOfTabs) {
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


