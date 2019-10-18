package com.example.erikh.reach.ui.run;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;

import android.util.Log;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.erikh.reach.BuildConfig;
import com.example.erikh.reach.Checkpoint;
import com.example.erikh.reach.CurrentRun;
import com.example.erikh.reach.GlideApp;
import com.example.erikh.reach.MapURL;
import com.example.erikh.reach.NFCPermissionDialog;
import com.example.erikh.reach.R;
import com.example.erikh.reach.CheckpointDatabase;

import android.nfc.NfcAdapter;
import android.view.View;

import android.widget.Button;
import android.widget.Chronometer;

import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.erikh.reach.Run;

public class RunActivity extends AppCompatActivity {

import uk.co.senab.photoview.PhotoViewAttacher;


    Context context;

    public static final String TAG = "RunActivity";

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    int width, height;
    String serialNumber;
    CheckpointDatabase checkpoints;

    ImageView mapImageView;
    ProgressBar progressBar;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    //Button startButton;
    //Button stopButton;
    //Button resetButton;

    String API_key;


    public static Run run;
    CurrentRun cRun;
    MapURL mapURL;
    String oldURL = "";

    NFCPermissionDialog nfcDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        checkpoints = CheckpointDatabase.getCheckpointDatabase();

        context = getApplicationContext();

        nfcDialog = new NFCPermissionDialog();


//        textView = (TextView) findViewById(R.id.NFC_info);
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        nfcAdapter = manager.getDefaultAdapter();

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC to perform that action",
                    Toast.LENGTH_LONG).show();
        }

        else if(!nfcAdapter.isEnabled()){
            createDialogWindow();

        } else{
            pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

        mapImageView = (ImageView) findViewById(R.id.map_image);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        API_key = BuildConfig.MapquestAPIKey;

        Log.d(TAG, Run.toString(run));

        cRun = new CurrentRun(run.getCheckpoints());
        Log.d(TAG, CurrentRun.toString(cRun));


        mapImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Remove it here unless you want to get this callback for EVERY
                //layout pass, which can get you into infinite loops if you ever
                //modify the layout from within this method.
                mapImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //Now you can get the width and height from content
                height = mapImageView.getHeight();
                width = mapImageView.getWidth();

                mapImageView.setMaxHeight(height);
                mapImageView.setMaxWidth(width);


                mapURL = new MapURL(cRun, width, height);
                String url = mapURL.getMapURL();
                if(oldURL.isEmpty()){
                    oldURL = url;
                }

                Log.d(TAG, "Map url: " + url);

                progressBar.setVisibility(View.VISIBLE);

                setMap(url, context, mapImageView);
            }
        });

        chronometer = findViewById(R.id.chronometer);

        //Buttons for testing, can be removed and connect the methods to events in the app later
        /*
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        */


        PhotoViewAttacher photoAttacher;
        photoAttacher= new PhotoViewAttacher(mapImageView);
        photoAttacher.update();
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC support to perform that action",
                    Toast.LENGTH_LONG).show();
        }

        else if(!nfcAdapter.isEnabled()){

        } else{
            try{
                nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
            } catch(NullPointerException e){
                Log.d(TAG, e.toString());
                pendingIntent = PendingIntent.getActivity(this, 0,
                        new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            }
        }

    }

    @Override
    protected void onPause(){
        super.onPause();

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC to perform that action",
                    Toast.LENGTH_LONG).show();
        }

        else if(!nfcAdapter.isEnabled()){

        } else{
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] tagID = tag.getId();

        serialNumber = byteToHex(tagID);

        Log.d(TAG, "Byte array: " + serialNumber);

        Checkpoint checkpoint = checkpoints.getCheckpointFromSerial(serialNumber.trim());
        String name = checkpoint.getName();
        Log.d(TAG, name);
        Toast.makeText(this, "Tag " + name + " has been scanned",
                Toast.LENGTH_SHORT).show();

        updateMap(checkpoint);

    }

    private String byteToHex(byte[] byteArray){
        StringBuilder tagSerialNumber = new StringBuilder();

        for (byte hexByte : byteArray) {
            String s = Integer.toHexString(((int) hexByte & 0xff));
            if (s.length() == 1){
                s= '0' + s;
            }
            tagSerialNumber.append(s).append(' ');
        }
        return tagSerialNumber.toString();
    }

    private void updateMap(Checkpoint checkpoint){
        cRun.updateCheckpointScannedStatus(checkpoint, true);
        mapURL.updateURL(cRun);
        progressBar.setVisibility(View.VISIBLE);

        setMap(mapURL.getMapURL(), context, mapImageView);

    }

    private void setMap(String mapURL, Context con, ImageView mapIV){
        Log.d(TAG, oldURL);
        GlideApp.with(con).load(mapURL)
                .thumbnail(GlideApp
                    .with(con)
                    .load(oldURL)
                    .fitCenter()
            ).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "Glide load failed");
                    return false; // thumbnail was not shown, do as usual

                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "Glide resource ready");
                    return false; // thumbnail was not shown, do as usual
                }
            }).into(mapIV);
        oldURL = mapURL;
        Log.d(TAG,oldURL);
    }

    //region CHRONOMETER
    /*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton: {
                startChronometer();
                break;
            }
            case R.id.stopButton:{
                stopChronometer();
                break;
            }
            case R.id.resetButton: {
                resetChronometer();
                break;
            }
        }
    }
     */

    private void startChronometer() {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    private void stopChronometer() {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            Log.d("Chronometer", "Time:" + ((SystemClock.elapsedRealtime() - chronometer.getBase())/1000));
            running = false;
        }
    }

    private void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }


    private void createDialogWindow(){
        new AlertDialog.Builder(RunActivity.this)
                .setTitle(R.string.NFC_question)
                .setMessage(R.string.NFC_subtitle)
                .setPositiveButton(R.string.enable_nfc, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(R.drawable.ic_nfc_white_24dp)
                .show();
    }
}
