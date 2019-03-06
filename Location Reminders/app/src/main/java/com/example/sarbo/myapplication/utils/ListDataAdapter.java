package com.example.sarbo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//custom adapter
public class ListDataAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ListDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView ID, TASK, LOCATION, NOTE;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_layout, parent, false);
            layoutHandler = new LayoutHandler();
            //   layoutHandler.ID = (TextView)row.findViewById(R.id.TVid);
            layoutHandler.TASK = (TextView) row.findViewById(R.id.TVtask);
            layoutHandler.LOCATION = (TextView) row.findViewById(R.id.TVloc);
            layoutHandler.NOTE = (TextView) row.findViewById(R.id.TVnote);
            row.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) row.getTag();
        }
        DataProvider dataProvider = (DataProvider) this.getItem(position);
        // layoutHandler.ID.setText(dataProvider.get_id());
        layoutHandler.TASK.setText(dataProvider.getTaskname());
        layoutHandler.LOCATION.setText(dataProvider.getLocation());
        layoutHandler.NOTE.setText(dataProvider.getNote());

        return row;
    }
}
