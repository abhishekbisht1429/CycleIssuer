package com.example.cycletracker.home.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cycletracker.R;
import com.example.cycletracker.lock.activity.LockActivity;
import com.example.cycletracker.util.Utility;
import com.example.cycletracker.util.WApiConsts;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Utility.getGraphInstance(getApplicationContext()).inject(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private void startLockActivity(int cycleId) {
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(WApiConsts.JSON_KEY_CYCLE_ID, cycleId);
        startActivity(intent);
        finish();
    }
}
