package com.example.cycletracker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.os.Bundle;
import com.example.cycletracker.MyApplication;
import com.example.cycletracker.R;
import com.example.cycletracker.dagger.activity.ActivityModule;
import com.example.cycletracker.dagger.activity.ActivitySubcomponent;

public class MainActivity extends AppCompatActivity {

    private ActivitySubcomponent activitySubcomponent;
    private NavController navController;
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

        navController = Navigation.findNavController(this, R.id.fragment_nav_host);
    }

    public ActivitySubcomponent getActivitySubcomponent() {
        return activitySubcomponent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!navController.popBackStack()) {
            finish();
        }
    }
}
