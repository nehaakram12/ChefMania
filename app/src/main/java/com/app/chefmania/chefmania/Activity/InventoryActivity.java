package com.app.chefmania.chefmania.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.chefmania.chefmania.Adapter.InventoryActivityPagerAdapter;
import com.app.chefmania.chefmania.Fragment.InventoryAddNewFragment;
import com.app.chefmania.chefmania.Fragment.InventoryEditFragment;
import com.app.chefmania.chefmania.Fragment.InventoryViewAllFragment;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
public class InventoryActivity extends AppCompatActivity implements InventoryViewAllFragment.OnFragmentInteractionListener,InventoryAddNewFragment.OnFragmentInteractionListener,InventoryEditFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<TabLayout.Tab> tabIdHistory = new ArrayList<TabLayout.Tab>();
    private TabLayout.Tab view_all;
    private TabLayout.Tab add_new;
    private TabLayout.Tab edit_details;

    public String inventory_item_id=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        setContentView(R.layout.activity_inventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.bringToFront();

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        view_all = tabLayout.newTab().setIcon(R.mipmap.view_red);
        add_new = tabLayout.newTab().setIcon(R.mipmap.add_grey);
        edit_details = tabLayout.newTab().setIcon(R.mipmap.edit_grey);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimary));

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        tabLayout.addTab(view_all);
        tabLayout.addTab(add_new);
        tabLayout.addTab(edit_details);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        tabIdHistory.add(view_all);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        InventoryActivityPagerAdapter mainActivityPagerAdapter = new InventoryActivityPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mainActivityPagerAdapter);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DISABLING THE SWIPESS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition())
                {
                    case 0:
                        view_all.setIcon(R.mipmap.view_red);
                        add_new.setIcon(R.mipmap.add_grey);
                        edit_details.setIcon(R.mipmap.edit_grey);
                        tabIdHistory.remove(tab);
                        tabIdHistory.add(tab);
                        break;
                    case 1:
                        view_all.setIcon(R.mipmap.view_grey);
                        add_new.setIcon(R.mipmap.add_red);
                        edit_details.setIcon(R.mipmap.edit_grey);
                        tabIdHistory.remove(tab);
                        tabIdHistory.add(tab);
                        break;
                    case 2:
                        view_all.setIcon(R.mipmap.view_grey);
                        add_new.setIcon(R.mipmap.add_grey);
                        edit_details.setIcon(R.mipmap.edit_red);
                        tabIdHistory.remove(tab);
                        tabIdHistory.add(tab);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (tabIdHistory.size() > 0)
        {
            tabIdHistory.remove(tabIdHistory.size() - 1);
            if(tabIdHistory.size() > 0)
            {
                tabIdHistory.get(tabIdHistory.size()-1).select();
            }
            else
            {
                FragmentManager mgr = getSupportFragmentManager();
                if (mgr.getBackStackEntryCount() == 0) {
                    super.onBackPressed();
                } else {
                    mgr.popBackStack();
                }
            }
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getInventory_item_id() {
        return inventory_item_id;
    }

    public void setInventory_item_id(String inventory_item_id) {
        this.inventory_item_id = inventory_item_id;
    }
}
