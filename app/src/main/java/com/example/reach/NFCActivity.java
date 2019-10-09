package com.example.reach;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.erikh.reach.R;

import android.nfc.NfcAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NFCActivity extends AppCompatActivity {

    public static final String TAG = "NFCActivity";

    private TextView textView;
    private NfcAdapter nfcAdapter;

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
            onNewIntent(getIntent());
        }

    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
            Parcelable[] nfcMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(nfcMessages != null){
                NdefMessage[] messages = new NdefMessage[nfcMessages.length];
                for(int i = 0; i<nfcMessages.length;i++){
                    messages[i] = (NdefMessage) nfcMessages[i];
                    System.out.println(messages);
                }

            }
        }
    }

}
