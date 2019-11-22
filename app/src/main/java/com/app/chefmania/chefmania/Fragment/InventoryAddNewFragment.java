package com.app.chefmania.chefmania.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.chefmania.chefmania.Activity.InventoryActivity;
import com.app.chefmania.chefmania.Model.Inventory;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InventoryAddNewFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText name, price, quantity, availability, expiry_date,threshold;
    Button addbutton;
    ViewPager viewpager;
    DatabaseReference db;

    public InventoryAddNewFragment() {
        // Required empty public constructor
    }

    public static InventoryAddNewFragment newInstance(String param1, String param2) {
        InventoryAddNewFragment fragment = new InventoryAddNewFragment();
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
        return inflater.inflate(R.layout.fragment_inventory_add_new, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        name = (EditText) view.findViewById(R.id.name);
        price = (EditText) view.findViewById(R.id.price);
        quantity = (EditText) view.findViewById(R.id.quantitiy);
        availability = (EditText) view.findViewById(R.id.availability);
        threshold = (EditText) view.findViewById(R.id.threshold);
        expiry_date = (EditText) view.findViewById(R.id.expiry_date);
        addbutton = (Button) view.findViewById(R.id.addbutton);
        viewpager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inventory item= new Inventory(name.getText().toString(),Integer.parseInt(price.getText().toString()),Integer.parseInt(quantity.getText().toString()),Integer.parseInt(availability.getText().toString()),Integer.parseInt(threshold.getText().toString()),expiry_date.getText().toString());
                DatabaseReference newRef = db.child("Inventory").push();
                newRef.setValue(item);
                Toast.makeText(getContext(), "Item added successfully!", Toast.LENGTH_SHORT).show();
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
}
