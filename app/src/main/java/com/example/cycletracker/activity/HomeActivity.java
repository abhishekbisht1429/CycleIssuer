package com.example.cycletracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.ui.login.LoginViewModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeActivity extends AppCompatActivity {

    Button scanBtn;
    Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scanBtn = findViewById(R.id.btn_scan);
        logoutButton = findViewById(R.id.btn_logout);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(HomeActivity.this).initiateScan();
            }
        });

        logoutButton.setOnClickListener((View view)->{

            new AsyncTask<Application, Void, Void>() {

                @Override
                protected Void doInBackground(Application... applications) {
                    DataRepository.getInstance(applications[0]).logout();

                    return null;
                }
            }.execute(this.getApplication());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
