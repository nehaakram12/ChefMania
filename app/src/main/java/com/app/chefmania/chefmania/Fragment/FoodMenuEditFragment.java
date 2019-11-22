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
import android.widget.ListView;
import android.widget.Toast;

import com.app.chefmania.chefmania.Activity.EmployeeActivity;
import com.app.chefmania.chefmania.Activity.InventoryActivity;
import com.app.chefmania.chefmania.Activity.FoodMenuActivity;
import com.app.chefmania.chefmania.Adapter.FoodItemListAdapter;
import com.app.chefmania.chefmania.Adapter.FoodMenuListAdapter;
import com.app.chefmania.chefmania.Model.Employee;
import com.app.chefmania.chefmania.Model.FoodItem;
import com.app.chefmania.chefmania.Model.Inventory;
import com.app.chefmania.chefmania.Model.FoodMenu;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class FoodMenuEditFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText name, type, time, price, availability;
    String sname, stype, stime;
    int sprice, savailability;
    Button editbutton;
    ViewPager viewpager;
    DatabaseReference db,dbmenu,dbinventory,dbmenuitems;

    FoodItemListAdapter customAdapter;
    ArrayList<FoodItem> listArray = new ArrayList<FoodItem>(5);
    String item_id;

    public FoodMenuEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodMenuEditFragment newInstance(String param1, String param2) {
        FoodMenuEditFragment fragment = new FoodMenuEditFragment();
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
        db = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_menu_edit, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listArray.clear();
        FoodMenuActivity ia= ((FoodMenuActivity) getActivity());
        item_id=ia.food_menu_item_id;

        dbmenu= FirebaseDatabase.getInstance().getReference("Menu");
        dbmenuitems= FirebaseDatabase.getInstance().getReference("MenuDetails");
        dbinventory= FirebaseDatabase.getInstance().getReference("Inventory");

        ListView listView = (ListView) view.findViewById(R.id.foodeditlv);

        name = (EditText) view.findViewById(R.id.name);
        type = (EditText) view.findViewById(R.id.type);
        time = (EditText) view.findViewById(R.id.time);
        price = (EditText) view.findViewById(R.id.price);
        availability = (EditText) view.findViewById(R.id.availability);
        editbutton = (Button) view.findViewById(R.id.editbutton);
        viewpager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        customAdapter = new FoodItemListAdapter(this.getContext(),listArray);
        listView.setAdapter(customAdapter);

        if(item_id!=null){
            addMenuChildEventListener();
            addMenuItemChildEventListener();
            addInventoryChildEventListener();
        }

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = dbmenu.child(item_id);
                FoodMenu item= new FoodMenu(item_id,name.getText().toString(),type.getText().toString(),Integer.parseInt(price.getText().toString()),time.getText().toString(),Integer.parseInt(availability.getText().toString()));
                myRef.setValue(item);

                dbmenuitems.child(item_id).removeValue();
                customAdapter.additemstodb(item_id);

                Toast.makeText(getContext(), "Item edited successfully!", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(getActivity(), FoodMenuActivity.class);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void addMenuChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(item_id)) {
                    sname = (String) dataSnapshot.child("name").getValue();
                    stype = (String) dataSnapshot.child("dish_type").getValue();
                    stime = (String) dataSnapshot.child("prep_time").getValue();
                    sprice = dataSnapshot.child("price").getValue(Integer.class);
                    savailability = dataSnapshot.child("availability").getValue(Integer.class);

                    name.setText(sname);
                    type.setText(stype);
                    time.setText(stime);
                    price.setText(String.valueOf(sprice));
                    availability.setText(String.valueOf(savailability));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                sname = (String) dataSnapshot.child("name").getValue();
                stype = (String) dataSnapshot.child("dish_type").getValue();
                stime = (String) dataSnapshot.child("prep_time").getValue();
                sprice = dataSnapshot.child("price").getValue(Integer.class);
                savailability = dataSnapshot.child("availability").getValue(Integer.class);

                name.setText(sname);
                type.setText(stype);
                time.setText(stime);
                price.setText(String.valueOf(sprice));
                availability.setText(String.valueOf(savailability));
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
        dbmenu.addChildEventListener(childListener);
    }

    private void addMenuItemChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    customAdapter.add(dataSnapshot.getKey(),(String) dataSnapshot.child("itemname").getValue(),dataSnapshot.child("quantity").getValue(Integer.class));
                    customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(item_id)){
                    customAdapter.add(item_id,(String) dataSnapshot.child("itemname").getValue(),dataSnapshot.child("quantity").getValue(Integer.class));
                    customAdapter.notifyDataSetChanged();
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
        dbmenuitems.child(item_id).addChildEventListener(childListener);
    }

    private void addInventoryChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                customAdapter.add((String) dataSnapshot.getKey(),(String) dataSnapshot.child("name").getValue(),0);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                customAdapter.add((String) dataSnapshot.getKey(),(String) dataSnapshot.child("name").getValue(),0);
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
        dbinventory.addChildEventListener(childListener);
    }

}
