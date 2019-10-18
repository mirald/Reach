package com.example.erikh.reach.ui.run;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.erikh.reach.CheckpointDatabase;
import com.example.erikh.reach.MainActivity;
import com.example.erikh.reach.R;
import com.example.erikh.reach.Run;

import java.util.ArrayList;

public class listItem extends ArrayAdapter<Run> {

    public listItem(Context context, int layout, ArrayList<Run> runList) {
        super(context, layout, runList);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Run run = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.nameId);
        TextView estimatedTime = (TextView) convertView.findViewById(R.id.estimatedTimeId);
        TextView location = (TextView) convertView.findViewById(R.id.locationId);
        TextView checkpointNumber = (TextView) convertView.findViewById(R.id.checkpointsId);
        TextView closestCheckpoint = (TextView) convertView.findViewById(R.id.closestId);



        name.setText(run.getName());
        estimatedTime.setText(run.getEstimateAsString());
        location.setText(run.getLocation());
        checkpointNumber.setText(run.getNumberOfCheckpoints() + "");
        float[] tempArr = new float[]{MainActivity.xcord, MainActivity.ycord};
        closestCheckpoint.setText(CheckpointDatabase.getCheckpointDatabase().getClosestCheckpoint(tempArr).getName());
        Log.d("Hejhoj", "getView: ");


        return convertView;
    }
}
