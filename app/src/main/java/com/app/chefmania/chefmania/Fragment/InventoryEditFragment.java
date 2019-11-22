package com.app.chefmania.chefmania.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.chefmania.chefmania.Activity.InventoryActivity;
import com.app.chefmania.chefmania.Model.Inventory;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class InventoryEditFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText name, price, quantity, availability, expiry_date,threshold;
    String sname, sexpiry_date, remove_notification_id;
    int sprice, squantity,savailability,sthreshold;
    Button editbutton;
    ViewPager viewpager;
    DatabaseReference db,dbNotification;

    String item_id=null;

    public InventoryEditFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InventoryEditFragment newInstance(String param1, String param2) {
        InventoryEditFragment fragment = new InventoryEditFragment();
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
        return inflater.inflate(R.layout.fragment_inventory_edit, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        db= FirebaseDatabase.getInstance().getReference("Inventory");
        dbNotification= FirebaseDatabase.getInstance().getReference("Notification");
        addChildEventListener();
        addNotificationChildEventListener();

        name = (EditText) view.findViewById(R.id.name);
        price = (EditText) view.findViewById(R.id.price);
        quantity = (EditText) view.findViewById(R.id.quantitiy);
        availability = (EditText) view.findViewById(R.id.availability);
        threshold = (EditText) view.findViewById(R.id.threshold);
        expiry_date = (EditText) view.findViewById(R.id.expiry_date);
        editbutton = (Button) view.findViewById(R.id.editbutton);
        viewpager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        InventoryActivity ia= ((InventoryActivity) getActivity());
        item_id=ia.inventory_item_id;

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inventory item= new Inventory(name.getText().toString(),Integer.parseInt(price.getText().toString()),Integer.parseInt(quantity.getText().toString()),Integer.parseInt(availability.getText().toString()),Integer.parseInt(threshold.getText().toString()),expiry_date.getText().toString());
                DatabaseReference newRef = db.child(item_id);
                newRef.setValue(item);

                if( Integer.parseInt(quantity.getText().toString()) > Integer.parseInt(threshold.getText().toString()) ) {

                    if(remove_notification_id!=null)
                    {
                        DatabaseReference mydb= FirebaseDatabase.getInstance().getReference("Notification");
                        DatabaseReference myRef = mydb.child(remove_notification_id);
                        myRef.removeValue();

                        Toast.makeText(getContext(), "Item out of low stock level now!", Toast.LENGTH_SHORT).show();
                    }
                }
                if( Integer.parseInt(quantity.getText().toString()) < Integer.parseInt(threshold.getText().toString()) ) {

                    if(remove_notification_id==null)
                    {
                        DatabaseReference mydb= FirebaseDatabase.getInstance().getReference();
                        DatabaseReference myRefNew = mydb.child("Notification").push();
                        Map<String,Object> taskMap = new HashMap<>();
                        taskMap.put("item_id", item_id);
                        taskMap.put("item_name", name.getText().toString());
                        taskMap.put("read", 0);
                        myRefNew.setValue(taskMap);
                    }
                }

                Toast.makeText(getContext(), "Item edited successfully!", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(getActivity(), InventoryActivity.class);
                (getActivity()).startActivity(refresh);
                (getActivity()).finish();
            }
        });

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
                if(dataSnapshot.child("item_id").getValue().toString().equals(item_id)) {
                    remove_notification_id = dataSnapshot.getKey();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("item_id").getValue().toString().equals(item_id)) {
                    remove_notification_id = dataSnapshot.getKey();
                }
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


    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(item_id)) {
                    sname = (String) dataSnapshot.child("name").getValue();
                    sprice = dataSnapshot.child("price").getValue(Integer.class);
                    squantity = dataSnapshot.child("quantity").getValue(Integer.class);
                    savailability = dataSnapshot.child("availability").getValue(Integer.class);
                    sthreshold = dataSnapshot.child("threshold").getValue(Integer.class);
                    sexpiry_date = (String) dataSnapshot.child("expirydate").getValue();

                    name.setText(sname);
                    price.setText(String.valueOf(sprice));
                    quantity.setText(String.valueOf(squantity));
                    availability.setText(String.valueOf(savailability));
                    threshold.setText(String.valueOf(sthreshold));
                    expiry_date.setText(sexpiry_date);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(item_id)) {
                    sname = (String) dataSnapshot.child("name").getValue();
                    sprice = dataSnapshot.child("price").getValue(Integer.class);
                    squantity = dataSnapshot.child("quantity").getValue(Integer.class);
                    savailability = dataSnapshot.child("availability").getValue(Integer.class);
                    sthreshold = dataSnapshot.child("threshold").getValue(Integer.class);
                    sexpiry_date = (String) dataSnapshot.child("expirydate").getValue();

                    name.setText(sname);
                    price.setText(String.valueOf(sprice));
                    quantity.setText(String.valueOf(squantity));
                    availability.setText(String.valueOf(savailability));
                    threshold.setText(String.valueOf(sthreshold));
                    expiry_date.setText(sexpiry_date);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                String key = dataSnapshot.getKey();
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
        db.addChildEventListener(childListener);
    }


}
