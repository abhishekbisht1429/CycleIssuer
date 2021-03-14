package com.example.cycletracker.activity.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.activity.ViewModelFactory;
import com.example.cycletracker.activity.lock.LockActivity;
import com.example.cycletracker.fragment.HistoryFragment;
import com.example.cycletracker.fragment.HomeFragment;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.models.BookedCycleResp;
import com.example.cycletracker.util.WApiConsts;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter pagerAdapter;
    private BottomNavigationView bottomNavView;
    private HomeViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = ViewModelFactory.getInstance(getApplication()).create(HomeViewModel.class);
        toolbar = findViewById(R.id.toolbar_home_activity);
        viewPager2 = findViewById(R.id.viewpager_home_activity);
        bottomNavView = findViewById(R.id.bottom_nav_view_home_activity);

        setSupportActionBar(toolbar);

//        scanBtn.setOnClickListener((View view) -> {
//                new IntentIntegrator(HomeActivity.this).initiateScan();
//        });
//
//        logoutBtn.setOnClickListener((View view)-> {
//            viewModel.loginStateChanged(false);
//            viewModel.logout();
//        });
        pagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setUserInputEnabled(false);

        bottomNavView.setOnNavigationItemSelectedListener(this);

        viewModel.getLoginState().observe(this, (Boolean state)-> {
            if(state==false) {
                Toast.makeText(this, "logged out", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_home_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_home_activity_action_qrscan: {
                new IntentIntegrator(HomeActivity.this).initiateScan();

                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_home_bottom_nav_activity_home: {
                viewPager2.setCurrentItem(0);

                return true;
            }

            case R.id.action_history_bottom_nav_activity_home: {
                viewPager2.setCurrentItem(1);

                return true;
            }

            default: {
                return false;
            }
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        private static final int PAGE_NUM = 2;

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position) {
                case 0: {
                    return HomeFragment.newInstance();
                }

                case 1: {
                    return HistoryFragment.newInstance();
                }

                default: return null;
            }
        }

        @Override
        public int getItemCount() {
            return PAGE_NUM;
        }
    }
}
