package com.example.erikh.reach.ui.run;

import android.app.ActionBar;
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
import com.example.erikh.reach.MainActivity;
import com.example.erikh.reach.MapURL;
import com.example.erikh.reach.NFCPermissionDialog;
import com.example.erikh.reach.PassedRuns;
import com.example.erikh.reach.R;
import com.example.erikh.reach.CheckpointDatabase;

import android.nfc.NfcAdapter;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import android.widget.Chronometer;

import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.erikh.reach.Run;
import com.example.erikh.reach.UserInfo;

import org.w3c.dom.Text;

import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RunActivity extends AppCompatActivity {

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
    private TextView tV;
    private long pauseOffset;
    private boolean running;

    String API_key;


    public static Run run;
    CurrentRun cRun;
    MapURL mapURL;
    String oldURL = "";

    NFCPermissionDialog nfcDialog;

    TextView estimatedTime, totalCheckpoints;
    String name;

    UserInfo userInfo;

    LinearLayout remainingCheckpoints, info;

    TextView numberOfRemainingCheckpoints;

    int remCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        checkpoints = CheckpointDatabase.getCheckpointDatabase();

        context = getApplicationContext();

        userInfo = UserInfo.getInstance();

        name = run.getName();

        getSupportActionBar().setTitle(name);

        estimatedTime = (TextView) findViewById(R.id.estimated_time);
        totalCheckpoints = (TextView) findViewById(R.id.nr_of_checkpoints);

        remCheck = run.getNumberOfCheckpoints();

        estimatedTime.setText("Estimated time: " + run.getEstimateAsString());
        totalCheckpoints.setText("Checkpoints: " + remCheck);

        nfcDialog = new NFCPermissionDialog();

        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        nfcAdapter = manager.getDefaultAdapter();

        if(nfcAdapter == null){
            showToast("This application requires NFC", "long");
        }

        else if(!nfcAdapter.isEnabled()){
            createNFCDialogWindow();

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
                mapImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

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

        tV = findViewById(R.id.info_text);
        chronometer = findViewById(R.id.chronometer);
        remainingCheckpoints = findViewById(R.id.remCheck);
        info = findViewById(R.id.info);

        numberOfRemainingCheckpoints = findViewById(R.id.remaining_checkpoints);


        PhotoViewAttacher photoAttacher;
        photoAttacher= new PhotoViewAttacher(mapImageView);
        photoAttacher.update();
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(nfcAdapter == null){
            showToast("This application requires NFC", "long");
        }

        else if(!nfcAdapter.isEnabled()){
//            createNFCDialogWindow();

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
            showToast("This application requires NFC", "long");
        }

        else if(!nfcAdapter.isEnabled()){

        } else{
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    public void onBackPressed() {
        if(running){
            createEndDialogWindow();
        } else{
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] tagID = tag.getId();

        serialNumber = byteToHex(tagID);
        boolean exists = false;

        Log.d(TAG, "Byte array: " + serialNumber);

        Checkpoint checkpoint = checkpoints.getCheckpointFromSerial(serialNumber.trim());
        exists = cRun.getCurrentRun().containsKey(checkpoint);
        String name = checkpoint.getName();
        if(exists) {
            Log.d(TAG, name);

            showToast("Tag "+ name+ " has been scanned", "short");

            if (remCheck > 0) {
                remCheck--;
            }


            numberOfRemainingCheckpoints.setText("Remaining checkpoints: " + remCheck);

            if (cRun.isFirstTag()) {
                chronometer.setVisibility(View.VISIBLE);
                tV.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                remainingCheckpoints.setVisibility(View.VISIBLE);
                startChronometer();

            }

            updateMap(checkpoint);

            if (cRun.isFinished()) {
                String time = stopChronometer();
                createFinishedDialogWindow(time);
            }
        } else{
            showToast("This tag is not part of the map", "long");
        }
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

    private void startChronometer() {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    private String stopChronometer() {
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            Log.d("Chronometer", "Time:" + ((SystemClock.elapsedRealtime() - chronometer.getBase())/1000));
            running = false;

        }
        long time = SystemClock.elapsedRealtime() - chronometer.getBase();

        String time_as_string = "";
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time)%60;
        long hours  = TimeUnit.MILLISECONDS.toHours(time);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time)%60;
        if(!(hours == 0)){
            time_as_string = String.format("%s h ",hours);
        }

        if(!(minutes==0)){
            time_as_string = time_as_string + String.format("%s m ", minutes);
        }

        time_as_string = time_as_string + String.format("%s s", seconds);
        userInfo.addListPassedRuns(new PassedRuns(run.getName(), time_as_string, new Date()));
        return time_as_string;
    }

    private void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }


    private void createNFCDialogWindow(){
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

    private void createEndDialogWindow(){
        new AlertDialog.Builder(RunActivity.this)
                .setTitle(R.string.end_question)
                .setMessage(R.string.end_subtitle)
                .setPositiveButton(R.string.end_run, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void createFinishedDialogWindow(String timeLapsed){
        new AlertDialog.Builder(RunActivity.this)
                .setTitle(R.string.finished)
                .setMessage("You finished the course in: " + timeLapsed)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void showToast(String message, String length){
        Toast toast;
        if(length.equals("long")){
            toast = Toast.makeText(this, message,
                    Toast.LENGTH_LONG);
        }else{
            toast = Toast.makeText(this, message,
                    Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.BOTTOM, 0, 300);
        toast.show();
    }
}
