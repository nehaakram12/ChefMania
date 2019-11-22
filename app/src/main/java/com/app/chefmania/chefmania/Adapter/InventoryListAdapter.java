package com.app.chefmania.chefmania.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chefmania.chefmania.Activity.InventoryActivity;
import com.app.chefmania.chefmania.Model.DataModel;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InventoryListAdapter extends BaseAdapter {
    private static final String TAG = InventoryListAdapter.class.getSimpleName();
    Context mcontext;
    ArrayList<DataModel> listArray;
    ArrayList<String> listColors;
    ViewPager viewpager;

    public InventoryListAdapter() {
        listArray = new ArrayList<DataModel>(5);
//        listArray.add(new DataModel("name1",  "Java"));
    }

    public InventoryListAdapter(Context context, ArrayList<DataModel> listArray,ArrayList<String> listColors) {
        this.mcontext=context;
        this.listArray = listArray;
        this.listColors = listColors;
    }

    public int add(String id,String title, String subtitle, String color){
        listArray.add(new DataModel(id, title,  subtitle));
        listColors.add(color);
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

        LinearLayout container = (LinearLayout) view.findViewById(R.id.list_container);
        view.setBackgroundColor(Color.parseColor(listColors.get(index)));

//        Log.i("wutt", "list keys " + listKeys );
//        Log.i("wutt", "ids " + dataModel.getId() );
//
//        Set<String> set = new HashSet<String>(listKeys);
//        Log.i("wutt", String.valueOf( set.contains(dataModel.getId()) )     );
//        if (set.contains(dataModel.getId()))
//        {
//            Log.i("wutt", "low value id" + dataModel.getId() );
//            container.setBackgroundColor(Color.parseColor("#FD9590"));
//        }

        ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail);
        imageView.setImageResource(R.mipmap.inventory);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(dataModel.getTitle());

        TextView textView2 = (TextView) view.findViewById(R.id.subtitle);
        textView2.setText(dataModel.getSubtitle());

        Button editbutton = (Button) view.findViewById(R.id.editbutton);
        editbutton.setText("Edit");

        Button deletebutton = (Button) view.findViewById(R.id.deletebutton);
        deletebutton.setText("Delete");

        viewpager = ((InventoryActivity) mcontext).findViewById(R.id.viewPager);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryActivity ia= ((InventoryActivity) mcontext);
                ia.inventory_item_id=dataModel.getId().toString();
                viewpager.setCurrentItem(2);
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("Inventory");
                DatabaseReference newRef = db.child(dataModel.getId());
                newRef.removeValue();
                Toast.makeText(parent.getContext(), "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(mcontext, InventoryActivity.class);
                (mcontext).startActivity(refresh);
                ((InventoryActivity) mcontext).finish();
            }
        });

        return view;
    }
}
