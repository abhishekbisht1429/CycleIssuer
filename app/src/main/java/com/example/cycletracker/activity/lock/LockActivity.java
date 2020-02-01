package com.example.cycletracker.activity.lock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.activity.ViewModelFactory;
import com.example.cycletracker.util.WApiConsts;

public class LockActivity extends AppCompatActivity {

    Button unlockSwitch;
    LockViewModel viewModel;
    int cycleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        unlockSwitch = findViewById(R.id.btn_unlock);
        viewModel = ViewModelFactory.getInstance(getApplication()).create(LockViewModel.class);

        viewModel.getSwitchState().observe(this, (Boolean state)-> {
            Toast.makeText(this, state+"", Toast.LENGTH_SHORT).show();
        });

        Bundle args = getIntent().getExtras();
        if(args == null)
            args = savedInstanceState;
        cycleId = args.getInt(WApiConsts.JSON_KEY_CYCLE_ID);

        unlockSwitch.setOnClickListener((View v)-> {
            viewModel.lockStateChaged(cycleId, true);
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(WApiConsts.JSON_KEY_CYCLE_ID, cycleId);
        super.onSaveInstanceState(outState);
    }
}
