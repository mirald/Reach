package com.example.erikh.reach;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    RunList list;
    CheckpointDatabase cDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cDB = CheckpointDatabase.getCheckpointDatabase();

        list = RunList.getRunList();

        list.addRun(new Run("Lindholm pier", "Göteborg, Lindholmen", "00:07:20:20", "map1",
                new String[]{"mirandas kort", "blue tag", "154"}, cDB));

        list.addRun(new Run("Slottsskogen", "Göteborg, Linné", "00:22:57:20", "map2",
                new String[]{"lindholmen","nordstan","eriks kort","jens kort"}, cDB));

        list.addRun(new Run("Central Linné run", "Göteborg, Linné", "00:17:20:20", "map3",
                new String[]{"lindholmen","johans kort","nordstan","adams kort"}, cDB));

        list.addRun(new Run("Slottsskogen extended", "Göteborg, Majorna, Linné", "12:20:00:20", "map4",
                new String[]{"lindholmen","nordstan","eriks kort","jens kort", "lindholmen",
                        "johans kort","adams kort"}, cDB));

        list.addRun(new Run("Big Gothenburg ", "Göteborg", "00:30:47:20", "map5",
                new String[]{"mirandas kort", "blue tag", "154", "lindholmen",
                        "nordstan", "eriks kort","jens kort", "johans kort", "adams kort"}, cDB));




        /*
        Old runs if we want to fill out with more
        list.addRun(new Run("Inner Jens Run", "Göteborg", "00:30:20:20", "test/test",
                new String[]{"mirandas kort","Lindholmen","Nordstan", "Eriks Kort", "Jens Kort"}, cDB));

        list.addRun(new Run("The Ickland", "Göteborg", "00:20:20:20", "test/test",
                new String[]{"Lindholmen","Nordstan", "Eriks Kort", "Jens Kort"}, cDB));
        
        list.addRun(new Run("Not a City Run", "Värmland", "12:20:00:20", "test/test",
                new String[]{"mirandas kort","154","Lindholmen","Nordstan", "Jens Kort"}, cDB));


         */





        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
