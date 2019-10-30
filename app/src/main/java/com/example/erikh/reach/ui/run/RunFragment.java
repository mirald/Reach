package com.example.erikh.reach.ui.run;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.erikh.reach.MainActivity;
import com.example.erikh.reach.R;
import com.example.erikh.reach.Run;
import com.example.erikh.reach.RunList;

import java.util.ArrayList;

public class RunFragment extends Fragment {

    private RunViewModel runViewModel;

    Context context;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public static listItem runs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        runViewModel =
                ViewModelProviders.of(this).get(RunViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        runViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        context = getContext();

        RunList list = RunList.getRunList();
        final ArrayList<Run> runArrayList = list.getRunArrayList();

        runs = new listItem(context, R.layout.activity_main, runArrayList);

        final ListView listView = view.findViewById(R.id.list_id);
        listView.setAdapter(runs);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), RunActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                RunActivity.run = runArrayList.get(position);
                RunFragment.this.startActivity(myIntent);
            }
        });

    }
}