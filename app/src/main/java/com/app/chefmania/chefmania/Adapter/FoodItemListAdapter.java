package com.app.chefmania.chefmania.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.chefmania.chefmania.Model.DataModel;
import com.app.chefmania.chefmania.Model.FoodItem;
import com.app.chefmania.chefmania.Model.FoodMenu;
import com.app.chefmania.chefmania.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodItemListAdapter extends BaseAdapter {
    Context mcontext;
    ArrayList<FoodItem> listArray;
    int listPosititon;

    public FoodItemListAdapter() {
        listArray = new ArrayList<FoodItem>(5);
    }

    public FoodItemListAdapter(Context context, ArrayList<FoodItem> listArray) {
        this.mcontext=context;
        this.listArray = listArray;
    }

    class ViewHolder {
        TextView htext;
        EditText hedit;
    }

    public View getView(int index, View view, final ViewGroup parent) {
        listPosititon=index;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.food_list_item, parent, false);

            viewHolder.htext = (TextView) view.findViewById(R.id.itemname);
            viewHolder.hedit = (EditText) view.findViewById(R.id.itemquantitiy);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.htext.setText(listArray.get(index).getItemid());

        if(listArray.get(index).getQuantity()==0){
            viewHolder.hedit.setText("");
        }else{
            viewHolder.hedit.setText(listArray.get(index).getQuantity() + "");
        }
        viewHolder.hedit.setId(index);

        viewHolder.hedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    if(!TextUtils.isEmpty(Caption.getText().toString())){
                        listArray.get(position).setQuantity(Integer.parseInt(Caption.getText().toString()));
                    }
                }
            }
        });

        return view;
    }

    public int add(String id,String title, int price){
        boolean found=false;

        for(int i=0;i<listArray.size();i++){
            if(listArray.get(i).getMenuid().equals(id)){
                found=true;
            }
        }
        if(found==false)
        {
            listArray.add(new FoodItem(id, title,  price));
        }

        return 1;
    }

    public int additemstodb(String menuid){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("MenuDetails");
        for(int i=0;i<listArray.size();i++){
            if(listArray.get(i).getQuantity()>0){
                Map<String,Object> taskMap = new HashMap<>();
                taskMap.put("itemname", listArray.get(i).getItemid());
                taskMap.put("quantity", listArray.get(i).getQuantity());
                db.child(menuid).child(listArray.get(i).getMenuid()).setValue(taskMap);
            }
        }
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

}
