package com.example.cycletracker.activity.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.activity.ViewModelFactory;
import com.example.cycletracker.activity.lock.LockActivity;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.models.BookedCycleResp;
import com.example.cycletracker.util.WApiConsts;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    Button scanBtn;
    Button logoutBtn;
    private HomeViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = ViewModelFactory.getInstance(getApplication()).create(HomeViewModel.class);
        scanBtn = findViewById(R.id.btn_scan);
        logoutBtn = findViewById(R.id.btn_logout);
        scanBtn.setOnClickListener((View view) -> {
                new IntentIntegrator(HomeActivity.this).initiateScan();
        });

        logoutBtn.setOnClickListener((View view)-> {
            viewModel.loginStateChanged(false);
            viewModel.logout();
        });

        viewModel.getLoginState().observe(this, (Boolean state)-> {
            if(state==false) {
                Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void startLockActivity(int cycleId) {
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(WApiConsts.JSON_KEY_CYCLE_ID, cycleId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String qrcode = result.getContents();
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                ApiClient.getInstance().getCycleIssuerClient().book(qrcode)
                        .enqueue(new Callback<BookedCycleResp>() {
                            @Override
                            public void onResponse(Call<BookedCycleResp> call, Response<BookedCycleResp> response) {
                                if(response.isSuccessful()) {
                                    BookedCycleResp res = response.body();
                                    if(res!=null) {
                                        if(response.code()==200) {
                                            startLockActivity(res.getCycleId());
                                        } else {
                                            Toast.makeText(HomeActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<BookedCycleResp> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
