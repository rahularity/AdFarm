package com.example.rahul.adfarm;

/**
 * Created by Rahul on 6/27/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rahul on 6/15/2017.
 */

public class PageAdapter extends ArrayAdapter<Page> {
    public PageAdapter(Context context, ArrayList<Page> pages) {
        super(context, 0, pages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout, parent, false);
        }
        final Page currentPage = getItem(position);
        TextView nameText = (TextView) listItemView.findViewById(R.id.name);
        TextView categoryText = (TextView) listItemView.findViewById(R.id.category);
        TextView fanCountText = (TextView) listItemView.findViewById(R.id.fan_count);

        nameText.setText(currentPage.getmName());
        categoryText.setText(currentPage.getmCategory());
        fanCountText.setText(currentPage.getmFanCount());

        return listItemView;

    }
}
