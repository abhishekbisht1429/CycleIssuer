package com.example.cycletracker.lock.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.lock.viewmodel.LockViewModel;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.model.GenericResponse;
import com.example.cycletracker.util.WApiConsts;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LockActivity extends AppCompatActivity {

    Button unlockSwitch;
    Button lockSwitch;
    Button returncycleBtn;

    @Inject
    LockViewModel lockViewModel;

    int cycleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        unlockSwitch = findViewById(R.id.btn_unlock);
        lockSwitch = findViewById(R.id.btn_lock);
        returncycleBtn = findViewById(R.id.btn_return_cycle);

        lockViewModel.getSwitchState().observe(this, (Boolean state)-> {
            Toast.makeText(this, state+"", Toast.LENGTH_SHORT).show();
        });

        Bundle args = getIntent().getExtras();
        if(args == null)
            args = savedInstanceState;
        cycleId = args.getInt(WApiConsts.JSON_KEY_CYCLE_ID);

        unlockSwitch.setOnClickListener((View v)-> {
            lockViewModel.lockStateChaged(cycleId, true);
        });

        lockSwitch.setOnClickListener((View v) -> {
            lockViewModel.lockStateChaged(cycleId, false);
        });

        returncycleBtn.setOnClickListener((View view)-> {
            returnCycle();
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(WApiConsts.JSON_KEY_CYCLE_ID, cycleId);
        super.onSaveInstanceState(outState);
    }

    private void returnCycle() {
        ApiClient.getInstance().getCycleIssuerClient().returnCycle(cycleId)
                .enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(LockActivity.this, "Cycle returned", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
