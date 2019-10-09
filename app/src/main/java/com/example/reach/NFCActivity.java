package com.example.reach;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
            Toast.makeText(this, "NFC is enabled",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
