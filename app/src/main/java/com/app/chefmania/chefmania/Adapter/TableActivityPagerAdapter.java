package com.app.chefmania.chefmania.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.chefmania.chefmania.Fragment.TableAddNewFragment;
import com.app.chefmania.chefmania.Fragment.TableEditFragment;
import com.app.chefmania.chefmania.Fragment.TableViewAllFragment;

public class TableActivityPagerAdapter extends FragmentPagerAdapter {

    private int nooftabs;
    private Fragment home = new TableViewAllFragment();
    private Fragment upload = new TableAddNewFragment();
    private Fragment profile = new TableEditFragment();

    public TableActivityPagerAdapter(FragmentManager fm, int NumberOfTabs) {
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


