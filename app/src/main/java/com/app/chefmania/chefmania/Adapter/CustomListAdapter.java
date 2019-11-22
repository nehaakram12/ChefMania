package com.app.chefmania.chefmania.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chefmania.chefmania.Model.DataModel;
import com.app.chefmania.chefmania.R;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private static final String TAG = CustomListAdapter.class.getSimpleName();
    ArrayList<DataModel> listArray;

    public CustomListAdapter() {
        listArray = new ArrayList<DataModel>(5);
//        listArray.add(new DataModel("name1",  "Java"));
//        listArray.add(new DataModel("name2",  "Python"));
//        listArray.add(new DataModel("name3",  "Django"));
//        listArray.add(new DataModel("name4",  "Groovy"));
//        listArray.add(new DataModel("name5",  "Maven"));
    }

    public CustomListAdapter(ArrayList<DataModel> listArray) {
        this.listArray = listArray;
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
        imageView.setImageResource(R.mipmap.employee);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(dataModel.getTitle());

        TextView textView2 = (TextView) view.findViewById(R.id.subtitle);
        textView2.setText(dataModel.getSubtitle());

        Button button = (Button) view.findViewById(R.id.editbutton);
        button.setText("Edit");

        Button button2 = (Button) view.findViewById(R.id.deletebutton);
        button2.setText("Delete");

        // button click listener
        // this chunk of code will run, if user click the button
        // because, we set the click listener on the button only

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "string: " + dataModel.getTitle());
                Log.d(TAG, "int: " + dataModel.getSubtitle());

                Toast.makeText(parent.getContext(), "Edit button clicked: " + dataModel.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "string: " + dataModel.getTitle());
                Log.d(TAG, "int: " + dataModel.getSubtitle());

                Toast.makeText(parent.getContext(), "Delete button clicked: " + dataModel.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        // if you commented out the above chunk of code and
        // activate this chunk of code
        // then if user click on any view inside the list view (except button)
        // this chunk of code will execute
        // because we set the click listener on the whole view


//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "string: " + dataModel.getName());
//                Log.d(TAG, "int: " + dataModel.getAnInt());
//                Log.d(TAG, "double: " + dataModel.getaDouble());
//                Log.d(TAG, "otherData: " + dataModel.getOtherData());
//
//                Toast.makeText(parent.getContext(), "view clicked: " + dataModel.getOtherData(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }
}
