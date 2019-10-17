package com.example.erikh.reach.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.erikh.reach.PassedRuns;
import com.example.erikh.reach.R;
import com.example.erikh.reach.Run;

import java.util.ArrayList;

public class runedListItem extends ArrayAdapter<PassedRuns> {

    public runedListItem(Context context, int layout, ArrayList<PassedRuns> runList) {
        super (context, layout, runList);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.passedrunitem, parent, false);
        }

        //Run run = getItem(position);
        PassedRuns run = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.nameid2);
        TextView time = (TextView) convertView.findViewById(R.id.timeid);
        TextView length = (TextView) convertView.findViewById(R.id.lengthId);
        name.setText(run.getName());
        time.setText(run.getTime());
        length.setText(run.getLength());


        return convertView;
    }
}
