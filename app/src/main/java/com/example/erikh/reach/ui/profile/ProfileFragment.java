package com.example.erikh.reach.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.erikh.reach.PassedRuns;
import com.example.erikh.reach.R;
import com.example.erikh.reach.Run;
import com.example.erikh.reach.RunList;
import com.example.erikh.reach.UserInfo;
import com.example.erikh.reach.ui.run.listItem;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    Context context;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        context = getContext();




        UserInfo user = UserInfo.getInstance();

        final int km = user.getRunKm();





        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);


        RunList list = RunList.getRunList();
        ArrayList<Run> runArrayList = list.getRunArrayList();

        ArrayList<PassedRuns> listPassedRuns = user.getListPassedRuns();


        runedListItem runs = new runedListItem(context, R.layout.activity_main, listPassedRuns);

        final ListView listView = root.findViewById(R.id.list_id);
        listView.setAdapter(runs);




        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("You’ve run a total of " + km + "" + " km");









            }
        });





        return root;




    }



}