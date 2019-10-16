package com.example.erikh.reach.ui.run;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;

import com.example.erikh.reach.BuildConfig;
import com.example.erikh.reach.CurrentRun;
import com.example.erikh.reach.GlideApp;
import com.example.erikh.reach.MapURL;
import com.example.erikh.reach.R;
import com.example.erikh.reach.CheckpointDatabase;

import android.nfc.NfcAdapter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.erikh.reach.Run;

public class RunActivity extends AppCompatActivity {

    Context context;

    public static final String TAG = "RunActivity";

    private TextView textView;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    int width, height;
    String serialNumber;
    CheckpointDatabase checkpoints;

    ImageView mapImageView;
    ProgressBar progressBar;

    String API_key;

    public static Run run;
    CurrentRun cRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        checkpoints = CheckpointDatabase.getCheckpointDatabase();

        context = getApplicationContext();

//        textView = (TextView) findViewById(R.id.NFC_info);
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        nfcAdapter = manager.getDefaultAdapter();

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC to perform that action",
                    Toast.LENGTH_LONG).show();
        }

        else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this, "Turn on NFC to use this function",
                    Toast.LENGTH_SHORT).show();
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


                MapURL mapURL = new MapURL(cRun, width, height);
                String url = mapURL.getMapURL();

                Log.d(TAG, "Map url: " + url);

                progressBar.setVisibility(View.VISIBLE);

                GlideApp.with(context).load(url).placeholder(new ColorDrawable(ContextCompat.getColor(context, R.color.whiteBackground)))
                        .error(new ColorDrawable(ContextCompat.getColor(context,
                                R.color.whiteBackground))).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "Glide load failed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "Glide resource ready");
                        return false;
                    }
                }).into(mapImageView);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC to perform that action",
                    Toast.LENGTH_LONG).show();
        }

        else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this, "Turn on NFC to use this function",
                    Toast.LENGTH_SHORT).show();
        } else{
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
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
            Toast.makeText(this, "Turn on NFC to use this function",
                    Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "NFC scanned",
                Toast.LENGTH_SHORT).show();

        String name = checkpoints.getCheckpointFromSerial(serialNumber.trim()).getName();
        Log.d(TAG, name);
        Toast.makeText(this, name,
                Toast.LENGTH_SHORT).show();

    }

    public String byteToHex(byte[] byteArray){
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
}
