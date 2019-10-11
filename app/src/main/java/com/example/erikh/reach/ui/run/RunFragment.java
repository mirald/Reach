package com.example.erikh.reach.ui.run;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.erikh.reach.MainActivity;
import com.example.erikh.reach.R;

import java.util.ArrayList;

public class RunFragment extends Fragment {

    private RunViewModel runViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        runViewModel =
                ViewModelProviders.of(this).get(RunViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        runViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ArrayList<String> al = new ArrayList<String>();
        al.add("Inner city exploring");
        al.add("Got to go fast!");
        al.add("Outer city exploring");

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al);

        final ListView listView = view.findViewById(R.id.list_id);
        listView.setAdapter(itemsAdapter);

        //TODO: Create a new view for when clicking a list element
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), RunActivity.class);
                //myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                RunFragment.this.startActivity(myIntent);
            }
        });


    }
}