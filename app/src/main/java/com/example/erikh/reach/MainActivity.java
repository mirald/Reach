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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = RunList.getRunList();
<<<<<<< HEAD
        list.addRun(new Run("Test Name", "Göteborg", "00:20:20:20", "test/test"));
        list.addRun(new Run("Gamer", "Göteborg", "00:20:20:20", "test/test"));
=======
        list.addRun(new Run("Inner City Run", "Göteborg", "00:30:20:20", "test/test"));
        list.addRun(new Run("The Erland", "Göteborg", "00:20:20:20", "test/test"));
        list.addRun(new Run("Outer City Run", "Värmland", "12:20:00:20", "test/test"));
>>>>>>> 3427b7f7cfe036a0a3ce5d9fd21203e05dc24721

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
