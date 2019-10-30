package com.example.erikh.reach;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.erikh.reach.ui.run.RunFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity  {

    RunList list;
    CheckpointDatabase cDB;

    private FusedLocationProviderClient client;

    public static float xcord;
    public static float ycord;

    public static boolean isLocationGranted = false;
    public static boolean locationAccepted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();







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

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        Log.d("fel", requestCode + " " + permissions.length + " gr " + grantResults[0]);

        locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

        Log.d("fel", "DSOOSDODPASOASDPOPASOAS " + locationAccepted);

        if(locationAccepted == true){


            client = LocationServices.getFusedLocationProviderClient(this);





        /*

        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){


            Log.d("fel", "fel");


            return;

        }


         */








            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {


                    if(location != null){
                        String startstring = location.toString();
                        startstring = startstring.replace('.', ',');
                        String[] splitstring = startstring.split(" ");
                        String cords = splitstring[1];
                        cords.split(",");
                        String xcordString =cords.split(",")[0] + "." + cords.split(",")[1];
                        String ycordString =cords.split(",")[2] + "." + cords.split(",")[3];
                        xcord = Float.parseFloat(xcordString);
                        ycord = Float.parseFloat(ycordString);
                    }

                }
            });

        }





    }






}

