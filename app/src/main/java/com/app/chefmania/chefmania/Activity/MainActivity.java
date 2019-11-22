package com.app.chefmania.chefmania.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.chefmania.chefmania.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference dbNotification=FirebaseDatabase.getInstance().getReference("Notification");
    ImageButton inventorybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        inventorybutton = (ImageButton) findViewById(R.id.inventorybutton);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addNotificationChildEventListener();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inventory) {
            Intent i = new Intent(getApplicationContext(),InventoryActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_food_menu) {
            Intent i = new Intent(getApplicationContext(),FoodMenuActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_employee) {
            Intent i = new Intent(getApplicationContext(),EmployeeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_table) {
            Intent i = new Intent(getApplicationContext(),TableActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_tablet) {
            Intent i = new Intent(getApplicationContext(),TabletActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_assignment) {
            Intent i = new Intent(getApplicationContext(),AssignmentActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OpenInventoryActivity(View view) {
        Intent i = new Intent(getApplicationContext(),InventoryActivity.class);
        startActivity(i);
    }

    public void OpenFoodMenuActivity(View view) {
        Intent i = new Intent(getApplicationContext(),FoodMenuActivity.class);
        startActivity(i);
    }

    public void OpenEmployeeActivity(View view) {
        Intent i = new Intent(getApplicationContext(),EmployeeActivity.class);
        startActivity(i);
    }

    public void OpenTableActivity(View view) {
        Intent i = new Intent(getApplicationContext(),TableActivity.class);
        startActivity(i);
    }

    public void OpenTabletActivity(View view) {
        Intent i = new Intent(getApplicationContext(),TabletActivity.class);
        startActivity(i);
    }

    public void OpenAssignmentActivity(View view) {
        Intent i = new Intent(getApplicationContext(),AssignmentActivity.class);
        startActivity(i);
    }

    private void addNotificationChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                inventorybutton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Toast.makeText(getApplicationContext(), (String) dataSnapshot.child("item_name").getValue() + " item Low on stock! Check Inventory", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(), (String) dataSnapshot.child("item_name").getValue() + " item Low on stock! Check Inventory", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbNotification.addChildEventListener(childListener);
    }


}
