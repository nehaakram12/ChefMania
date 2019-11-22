package com.app.chefmania.chefmania.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.chefmania.chefmania.Adapter.CustomListAdapter;
import com.app.chefmania.chefmania.Adapter.InventoryListAdapter;
import com.app.chefmania.chefmania.Model.DataModel;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InventoryViewAllFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    InventoryListAdapter customAdapter;
    ArrayList<String> listKeys = new ArrayList<String>();
    ArrayList<DataModel> listArray = new ArrayList<DataModel>(5);
    ArrayList<String> listColors = new ArrayList<String>();
    DatabaseReference dbNotification,db;

    public InventoryViewAllFragment() {
        // Required empty public constructor
    }

    public static InventoryViewAllFragment newInstance(String param1, String param2) {
        InventoryViewAllFragment fragment = new InventoryViewAllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory_view_all, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listKeys.clear();
        listArray.clear();
        listColors.clear();
        dbNotification= FirebaseDatabase.getInstance().getReference("Notification");
        db= FirebaseDatabase.getInstance().getReference("Inventory");
        ListView listView = (ListView) view.findViewById(R.id.lv);
//        listArray.add(new DataModel("asdhkasdnklas","fries",  "Java"));

        customAdapter = new InventoryListAdapter(this.getContext(),listArray,listColors);
        listView.setAdapter(customAdapter);
        boolean found=false;

        addNotificationChildEventListener();
        addItemsChildEventListener();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void addNotificationChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listKeys.add(dataSnapshot.child("item_id").getValue(String.class));
                customAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), (String) dataSnapshot.child("item_name").getValue() + " item Low on stock! Check Inventory", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                listKeys.add(dataSnapshot.child("item_id").getValue(String.class));
                customAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), (String) dataSnapshot.child("item_name").getValue() + " item Low on stock! Check Inventory", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
//                int index = listKeys.indexOf(key);
//
//                if (index != -1) {
//                    listItems.remove(index);
//                    listKeys.remove(index);
//                    adapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbNotification.addChildEventListener(childListener);
    }

    private void addItemsChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String color="#EDEEE5";
                if(dataSnapshot.child("quantity").getValue(Integer.class)<dataSnapshot.child("threshold").getValue(Integer.class)){
                    color="#FD9590";
                }
                customAdapter.add((String) dataSnapshot.getKey(),(String) dataSnapshot.child("name").getValue(),(String) dataSnapshot.child("expirydate").getValue(),color);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String color="#EDEEE5";
                if(dataSnapshot.child("quantity").getValue(Integer.class)<dataSnapshot.child("threshold").getValue(Integer.class)){
                    color="#FD9590";
                }
                customAdapter.add((String) dataSnapshot.getKey(),(String) dataSnapshot.child("name").getValue(),(String) dataSnapshot.child("expirydate").getValue(),color);
                customAdapter.notifyDataSetChanged();
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
        db.addChildEventListener(childListener);
    }


}
