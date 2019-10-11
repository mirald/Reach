package com.example.erikh.reach.ui.home;

import android.content.Context;
import android.content.Intent;
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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
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
        ArrayList<Run> runArrayList = list.getRunArrayList();

        listItem runs = new listItem(context, R.layout.activity_main, runArrayList);

        final ListView listView = view.findViewById(R.id.list_id);
        listView.setAdapter(runs);

        //TODO: Create a new view for when clicking a list element
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                HomeFragment.this.startActivity(myIntent);
            }
        });


    }
}