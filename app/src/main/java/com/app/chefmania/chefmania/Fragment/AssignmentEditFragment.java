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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.chefmania.chefmania.Activity.AssignmentActivity;
import com.app.chefmania.chefmania.Activity.TableActivity;
import com.app.chefmania.chefmania.Model.Assignment;
import com.app.chefmania.chefmania.Model.DataModel;
import com.app.chefmania.chefmania.Model.Table;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignmentEditFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String item_id="lsdlsnc";

    int selected_table,selected_tablet,selected_employee;

    Spinner table,tablet,employee;
    Button editbutton;

    ArrayAdapter<String> tableAdapter,tabletAdapter,employeeAdapter;

    ArrayList<DataModel> listArray = new ArrayList<DataModel>(5);
    ArrayList<String> listKeys = new ArrayList<String>();

    ArrayList<String> listArrayTable = new ArrayList<String>(5);
    ArrayList<String> listKeysTable = new ArrayList<String>();

    ArrayList<String> listArrayTablet = new ArrayList<String>(5);
    ArrayList<String> listKeysTablet = new ArrayList<String>();

    ArrayList<String> listArrayEmployee = new ArrayList<String>(5);
    ArrayList<String> listKeysEmployee = new ArrayList<String>();

    DatabaseReference db,dbtable,dbtablet,dbemployee,dbassignment;
    private String stableid,stabletid,semployeeid,foundemployeeid;

    public AssignmentEditFragment() {
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
    public static AssignmentEditFragment newInstance(String param1, String param2) {
        AssignmentEditFragment fragment = new AssignmentEditFragment();
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
        return inflater.inflate(R.layout.fragment_assignment_edit, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        listArray.clear();
        listKeys.clear();
        listArrayTable.clear();
        listArrayTablet.clear();
        listArrayEmployee.clear();
        listKeysTable.clear();
        listKeysTablet.clear();
        listKeysEmployee.clear();

        db = FirebaseDatabase.getInstance().getReference();
        dbtable= FirebaseDatabase.getInstance().getReference("Table");
        dbtablet= FirebaseDatabase.getInstance().getReference("Tablet");
        dbemployee= FirebaseDatabase.getInstance().getReference("Employee");
        dbassignment = FirebaseDatabase.getInstance().getReference("Assignment");

        table = (Spinner) view.findViewById(R.id.table);
        tablet = (Spinner) view.findViewById(R.id.tablet);
        employee = (Spinner) view.findViewById(R.id.employee);
        editbutton = (Button) view.findViewById(R.id.editbutton);

        AssignmentActivity ia= ((AssignmentActivity) getActivity());
        item_id=ia.assignment_item_id;

//        viewpager = (ViewPager) getActivity().findViewById(R.id.viewPager);

        addAssignmentChildEventListener();
        addTableChildEventListener();
        addTabletChildEventListener();
        addEmployeeChildEventListener();

        tableAdapter = new ArrayAdapter<String>(getContext(), R.layout.layout_spinner_item, listArrayTable);
        tableAdapter.setDropDownViewResource(R.layout.layout_spinner_item);
        table.setAdapter(tableAdapter);

        tabletAdapter = new ArrayAdapter<String>(getContext(), R.layout.layout_spinner_item, listArrayTablet);
        tabletAdapter.setDropDownViewResource(R.layout.layout_spinner_item);
        tablet.setAdapter(tabletAdapter);

        employeeAdapter = new ArrayAdapter<String>(getContext(), R.layout.layout_spinner_item, listArrayEmployee);
        employeeAdapter.setDropDownViewResource(R.layout.layout_spinner_item);
        employee.setAdapter(employeeAdapter);

        table.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                selected_table=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tablet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                selected_tablet=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                selected_employee=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Assignment item= new Assignment(listArrayEmployee.get(selected_employee),listArrayTable.get(selected_table),listArrayTablet.get(selected_tablet));
                DatabaseReference newRef0 = dbassignment.child(item_id);
                newRef0.setValue(item);

                DatabaseReference newRef11 = dbtable.child(listKeysTable.get(listArrayTable.indexOf(stableid))).child("status");
                newRef11.setValue(1);
                DatabaseReference newRef1 = dbtable.child(listKeysTable.get(selected_table)).child("status");
                newRef1.setValue(0);

                DatabaseReference newRef22 = dbtablet.child(listKeysTablet.get(listArrayTablet.indexOf(stabletid))).child("status");
                newRef22.setValue(1);
                DatabaseReference newRef2 = dbtablet.child(listKeysTablet.get(selected_tablet)).child("status");
                newRef2.setValue(0);

                DatabaseReference newRef33 = dbemployee.child(listKeysEmployee.get(listArrayEmployee.indexOf(semployeeid))).child("availability");
                newRef33.setValue(1);
                DatabaseReference newRef3 = dbemployee.child(listKeysEmployee.get(selected_employee)).child("availability");
                newRef3.setValue(0);

                Toast.makeText(getContext(), "Item edited successfully!", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(getActivity(), AssignmentActivity.class);
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

    private void addTableChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("status").getValue(Integer.class)==1 || dataSnapshot.child("name").getValue(String.class).equals(stableid)) {
                    tableAdapter.add((String) dataSnapshot.child("name").getValue());
                    listKeysTable.add(dataSnapshot.getKey());
                    tableAdapter.notifyDataSetChanged();

                    table.setSelection(listArrayTable.indexOf(stableid));

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("status").getValue(Integer.class)==1 || dataSnapshot.child("name").getValue(String.class).equals(stableid)) {
                    tableAdapter.add((String) dataSnapshot.child("name").getValue());
                    listKeysTable.add(dataSnapshot.getKey());
                    tableAdapter.notifyDataSetChanged();
                }
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
        dbtable.addChildEventListener(childListener);
    }

    private void addTabletChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("status").getValue(Integer.class)==1 || dataSnapshot.child("name").getValue(String.class).equals(stabletid)) {
                    tabletAdapter.add((String) dataSnapshot.child("name").getValue());
                    listKeysTablet.add(dataSnapshot.getKey());
                    tabletAdapter.notifyDataSetChanged();

                    tablet.setSelection(listArrayTablet.indexOf(stabletid));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("status").getValue(Integer.class)==1 || dataSnapshot.child("name").getValue(String.class).equals(stabletid)) {
                    tabletAdapter.add((String) dataSnapshot.child("name").getValue());
                    listKeysTablet.add(dataSnapshot.getKey());
                    tabletAdapter.notifyDataSetChanged();
                }
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
        dbtablet.addChildEventListener(childListener);
    }

    private void addEmployeeChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("availability").getValue(Integer.class)==1 || dataSnapshot.child("name").getValue(String.class).equals(semployeeid)){
                    employeeAdapter.add((String) dataSnapshot.child("name").getValue());
                    listKeysEmployee.add(dataSnapshot.getKey());
                    employeeAdapter.notifyDataSetChanged();

                    employee.setSelection(listArrayEmployee.indexOf(semployeeid));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("availability").getValue(Integer.class)==1 || dataSnapshot.child("name").getValue(String.class).equals(semployeeid)){
                    employeeAdapter.add((String) dataSnapshot.child("name").getValue());
                    listKeysEmployee.add(dataSnapshot.getKey());
                    employeeAdapter.notifyDataSetChanged();
                }
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
        dbemployee.addChildEventListener(childListener);
    }

    private void addAssignmentChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(item_id)) {
                    stableid = (String) dataSnapshot.child("table_id").getValue();
                    stabletid = (String) dataSnapshot.child("tablet_id").getValue();
                    semployeeid = (String) dataSnapshot.child("e_id").getValue();

//                    db.child("Employee")
//                            .orderByChild("name")
//                            .equalTo(semployeeid)
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                @Override
//                                                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                                                                        foundemployeeid = childSnapshot.getKey();
//                                                                    }
//                                                                }
//                                                                @Override
//                                                                public void onCancelled(DatabaseError databaseError) {
//                                                                }
//                                                            });
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(item_id)) {
                    stableid = (String) dataSnapshot.child("table_id").getValue();
                    stabletid = (String) dataSnapshot.child("tablet_id").getValue();
                    semployeeid = (String) dataSnapshot.child("e_id").getValue();

                    table.setSelection(listArrayTable.indexOf(stableid));
                    tablet.setSelection(listArrayTablet.indexOf(stabletid));
                    employee.setSelection(listArrayEmployee.indexOf(semployeeid));
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
        dbassignment.addChildEventListener(childListener);
    }



}
