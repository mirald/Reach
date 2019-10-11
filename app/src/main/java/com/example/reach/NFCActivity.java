package com.example.reach;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

import com.example.erikh.reach.R;

import android.nfc.NfcAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NFCActivity extends AppCompatActivity {

    public static final String TAG = "NFCActivity";

    private TextView textView;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    StringBuilder tagSerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.NFC_info);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter == null){
            Toast.makeText(this, "The app requires NFC to perform that action",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        if(!nfcAdapter.isEnabled()){
            Toast.makeText(this, "Turn on NFC to use this function",
                    Toast.LENGTH_SHORT).show();
        } else{
            pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

    }

    @Override
    protected void onPause(){
        super.onPause();
        if(nfcAdapter != null){
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] tagID = tag.getId();

        tagSerialNumber = new StringBuilder();

        for (byte hexByte : tagID) {
            String s = Integer.toHexString(((int) hexByte & 0xff));
            if (s.length() == 1){
                s= '0' + s;
            }
            tagSerialNumber.append(s).append(' ');
        }

        Log.d(TAG, "Byte array: " + tagSerialNumber);
        Toast.makeText(this, "NFC scanned",
                Toast.LENGTH_SHORT).show();
        textView.setText(tagSerialNumber);

    }

}
