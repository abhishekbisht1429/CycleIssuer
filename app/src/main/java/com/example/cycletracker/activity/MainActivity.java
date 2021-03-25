package com.example.cycletracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cycletracker.MyApplication;
import com.example.cycletracker.R;
import com.example.cycletracker.dagger.activity.ActivityModule;
import com.example.cycletracker.dagger.activity.ActivitySubcomponent;
import com.example.cycletracker.lock.activity.LockActivity;
import com.example.cycletracker.util.WApiConsts;

public class MainActivity extends AppCompatActivity {

    private ActivitySubcomponent activitySubcomponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Utility.getGraphInstance(getApplicationContext()).inject(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activitySubcomponent = ((MyApplication)getApplication()).getAppComponent()
                .activitySubComponentBuilder()
                .activityModule(new ActivityModule(this))
                .build();
        activitySubcomponent.inject(this);
    }

    public ActivitySubcomponent getActivitySubcomponent() {
        return activitySubcomponent;
    }
    private void startLockActivity(int cycleId) {
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(WApiConsts.JSON_KEY_CYCLE_ID, cycleId);
        startActivity(intent);
        finish();
    }
}
