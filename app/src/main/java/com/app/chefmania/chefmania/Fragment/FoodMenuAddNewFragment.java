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

import com.app.chefmania.chefmania.Activity.InventoryActivity;
import com.app.chefmania.chefmania.Activity.FoodMenuActivity;
import com.app.chefmania.chefmania.Adapter.FoodItemListAdapter;
import com.app.chefmania.chefmania.Adapter.FoodMenuListAdapter;
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

public class FoodMenuAddNewFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private ListView myList;
    EditText name, type, time, price, availability;
    Button addbutton;
    ViewPager viewpager;
    DatabaseReference db,dbfooditem;

    FoodItemListAdapter customAdapter;
    ArrayList<FoodItem> listArray = new ArrayList<FoodItem>(5);

    public FoodMenuAddNewFragment() {
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
    public static FoodMenuAddNewFragment newInstance(String param1, String param2) {
        FoodMenuAddNewFragment fragment = new FoodMenuAddNewFragment();
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
        return inflater.inflate(R.layout.fragment_food_menu_add_new, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listArray.clear();
        db= FirebaseDatabase.getInstance().getReference("Menu");
        dbfooditem= FirebaseDatabase.getInstance().getReference("Inventory");
//        listArray.add(new FoodItem("farfaw","fasfaf",0));
//        ListView listView = (ListView) view.findViewById(R.id.lv);

        name = (EditText) view.findViewById(R.id.name);
        type = (EditText) view.findViewById(R.id.type);
        time = (EditText) view.findViewById(R.id.time);
        price = (EditText) view.findViewById(R.id.price);
        availability = (EditText) view.findViewById(R.id.availability);
        addbutton = (Button) view.findViewById(R.id.addbutton);
        viewpager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        myList = (ListView) getActivity().findViewById(R.id.foodaddlv);
        myList.setItemsCanFocus(true);
        customAdapter = new FoodItemListAdapter(this.getContext(),listArray);
        myList.setAdapter(customAdapter);

        addChildEventListener();

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = db.push();
                FoodMenu item= new FoodMenu(myRef.getKey(),name.getText().toString(),type.getText().toString(),Integer.parseInt(price.getText().toString()),time.getText().toString(),Integer.parseInt(availability.getText().toString()));
                myRef.setValue(item);

                customAdapter.additemstodb(myRef.getKey());

                Toast.makeText(getContext(), "Item added successfully!", Toast.LENGTH_SHORT).show();
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

    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                customAdapter.add((String) dataSnapshot.getKey(),(String) dataSnapshot.child("name").getValue(),0);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        dbfooditem.addChildEventListener(childListener);
    }
}
