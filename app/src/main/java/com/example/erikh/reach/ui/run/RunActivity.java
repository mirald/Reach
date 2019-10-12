package com.example.erikh.reach.ui.run;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.erikh.reach.BuildConfig;
import com.example.erikh.reach.GlideApp;
import com.example.erikh.reach.R;
import com.example.erikh.reach.CheckpointDatabase;

import android.nfc.NfcAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Map;

public class RunActivity extends AppCompatActivity {

    Context context;

    public static final String TAG = "RunActivity";

    private TextView textView;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    String serialNumber;
    CheckpointDatabase checkpoints;

    ImageView mapImageView;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        checkpoints = CheckpointDatabase.getCheckpointDatabase();

        context = getApplicationContext();

        textView = (TextView) findViewById(R.id.NFC_info);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC to perform that action",
                    Toast.LENGTH_LONG).show();
            textView.setText("Your phone is not supported");
        }

        if(!nfcAdapter.isEnabled()){
            Toast.makeText(this, "Turn on NFC to use this function",
                    Toast.LENGTH_SHORT).show();
            textView.setText("Turn on NFC");
        } else{
            pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

        String API_key = BuildConfig.MapquestAPIKey;
        Log.d(TAG, API_key);

        String mapURL = "https://www.mapquestapi.com/staticmap/v5/map?key="+API_key+"&center=Boston," +
                "MA&size=@2x";

        mapImageView = (ImageView) findViewById(R.id.map_image);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        //TODO add error and placeholder images
        GlideApp.with(context).load(mapURL).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(mapImageView);

    }

    @Override
    protected void onResume(){
        super.onResume();

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC to perform that action",
                    Toast.LENGTH_LONG).show();
        }

        if(!nfcAdapter.isEnabled()){
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

        if(!nfcAdapter.isEnabled()){
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
        textView.setText(name);
//        textView.setText(checkpoints.getCheckpoint(serialNumber).getName());

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
