package com.example.rahul.adfarm;

/**
 * Created by Rahul on 6/27/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rahul on 6/15/2017.
 */

public class PageAdapter extends BaseAdapter {


    Context ctx;
    LayoutInflater inflater;
    ArrayList<Page> objects;

    public PageAdapter(Context context, ArrayList<Page> pages) {
        ctx = context;
        objects = pages;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_layout, parent, false);
        }

        Page p = getPage(position);

        ((TextView) view.findViewById(R.id.name)).setText(p.mName);
        ((TextView) view.findViewById(R.id.category)).setText(p.mCategory);
        ((TextView) view.findViewById(R.id.fan_count)).setText(p.mFanCount);

        CheckBox check = (CheckBox) view.findViewById(R.id.cbBox);
        check.setOnCheckedChangeListener(myCheckChangList);
        check.setTag(position);
        check.setChecked(p.mBox);
        return view;
    }

    Page getPage(int position) {
        return ((Page) getItem(position));
    }

    ArrayList<Page> getBox() {
        ArrayList<Page> box = new ArrayList<Page>();
        for (Page p : objects) {
            if (p.mBox)
                box.add(p);
        }
        return box;
    }

    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getPage((Integer) buttonView.getTag()).mBox = isChecked;
        }
    };
}
