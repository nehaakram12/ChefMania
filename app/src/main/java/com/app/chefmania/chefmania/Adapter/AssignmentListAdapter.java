package com.app.chefmania.chefmania.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chefmania.chefmania.Activity.InventoryActivity;
import com.app.chefmania.chefmania.Activity.AssignmentActivity;
import com.app.chefmania.chefmania.Model.DataModel;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AssignmentListAdapter extends BaseAdapter {
    private static final String TAG = AssignmentListAdapter.class.getSimpleName();
    Context mcontext;
    ArrayList<DataModel> listArray;
    ViewPager viewpager;

    public AssignmentListAdapter() {
        listArray = new ArrayList<DataModel>(5);
//        listArray.add(new DataModel("name1",  "Java"));
//        listArray.add(new DataModel("name2",  "Python"));
//        listArray.add(new DataModel("name3",  "Django"));
//        listArray.add(new DataModel("name4",  "Groovy"));
//        listArray.add(new DataModel("name5",  "Maven"));
    }

    public AssignmentListAdapter(Context context, ArrayList<DataModel> listArray) {
        this.mcontext=context;
        this.listArray = listArray;
    }

    public int add(String id,String title, String subtitle){
        listArray.add(new DataModel(id, title,  subtitle));
        return 1;
    }

    public int getCount() {
        return listArray.size();    // total number of elements in the list
    }

    public Object getItem(int i) {
        return listArray.get(i);    // single item in the list
    }

    public long getItemId(int i) {
        return i;                   // index number
    }

    public View getView(int index, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        final DataModel dataModel = listArray.get(index);

        ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail);
        imageView.setImageResource(R.mipmap.assignment);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(dataModel.getTitle());

        TextView textView2 = (TextView) view.findViewById(R.id.subtitle);
        textView2.setText(dataModel.getSubtitle());

        Button editbutton = (Button) view.findViewById(R.id.editbutton);
        editbutton.setText("Edit");

        Button deletebutton = (Button) view.findViewById(R.id.deletebutton);
        deletebutton.setText("Delete");

        viewpager = ((AssignmentActivity) mcontext).findViewById(R.id.viewPager);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssignmentActivity ia= ((AssignmentActivity) mcontext);
                ia.assignment_item_id=dataModel.getId().toString();
                viewpager.setCurrentItem(2);
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Assignment");
                DatabaseReference newRef = db.child(dataModel.getId());
                newRef.removeValue();
                Toast.makeText(parent.getContext(), "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(mcontext, AssignmentActivity.class);
                (mcontext).startActivity(refresh);
                ((AssignmentActivity) mcontext).finish();
            }
        });

        return view;
    }

}
