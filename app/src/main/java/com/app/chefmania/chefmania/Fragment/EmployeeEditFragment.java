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
import com.app.chefmania.chefmania.Activity.EmployeeActivity;
import com.app.chefmania.chefmania.Model.Inventory;
import com.app.chefmania.chefmania.Model.Employee;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeEditFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    EditText name, salary, type, availability;
    String sname,stype;
    int ssalary, savailability;
    Button editbutton;
    ViewPager viewpager;
    DatabaseReference db;

    String item_id="lsdlsnc";


    public EmployeeEditFragment() {
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
    public static EmployeeEditFragment newInstance(String param1, String param2) {
        EmployeeEditFragment fragment = new EmployeeEditFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_edit, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        db= FirebaseDatabase.getInstance().getReference("Employee");
        addChildEventListener();

        name = (EditText) view.findViewById(R.id.name);
        salary = (EditText) view.findViewById(R.id.salary);
        type = (EditText) view.findViewById(R.id.type);
        availability = (EditText) view.findViewById(R.id.availability);
        editbutton = (Button) view.findViewById(R.id.editbutton);
        viewpager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        EmployeeActivity ia= ((EmployeeActivity) getActivity());
        item_id=ia.employee_item_id;

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee item= new Employee(name.getText().toString(),Integer.parseInt(salary.getText().toString()),type.getText().toString(),Integer.parseInt(availability.getText().toString()));
                DatabaseReference newRef = db.child(item_id);
                newRef.setValue(item);
                Toast.makeText(getContext(), "Item edited successfully!", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(getActivity(), EmployeeActivity.class);
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
                if(dataSnapshot.getKey().equals(item_id)) {
                    sname = (String) dataSnapshot.child("name").getValue();
                    ssalary = dataSnapshot.child("salary").getValue(Integer.class);
                    stype = (String) dataSnapshot.child("type").getValue();
                    savailability = dataSnapshot.child("availability").getValue(Integer.class);

                    name.setText(sname);
                    salary.setText(String.valueOf(ssalary));
                    type.setText(String.valueOf(stype));
                    availability.setText(String.valueOf(savailability));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(item_id)) {
                    sname = (String) dataSnapshot.child("name").getValue();
                    ssalary = dataSnapshot.child("salary").getValue(Integer.class);
                    stype = (String) dataSnapshot.child("type").getValue();
                    savailability = dataSnapshot.child("availability").getValue(Integer.class);

                    name.setText(sname);
                    salary.setText(String.valueOf(ssalary));
                    type.setText(String.valueOf(stype));
                    availability.setText(String.valueOf(savailability));
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
