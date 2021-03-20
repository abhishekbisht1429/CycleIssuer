package com.example.cycletracker.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cycletracker.MyApplication;
import com.example.cycletracker.R;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.model.BookedCycleResp;
import com.example.cycletracker.util.Utility;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends HomeBaseFragment implements Toolbar.OnMenuItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter pagerAdapter;
    private BottomNavigationView bottomNavView;
    private NavController navController;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        getHomeSubComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        pagerAdapter = new ViewPagerAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = view.findViewById(R.id.toolbar_home_activity);
        viewPager2 = view.findViewById(R.id.viewpager_home_activity);
        bottomNavView = view.findViewById(R.id.bottom_nav_view_home_activity);

//        toolbar.inflateMenu(R.menu.menu_toolbar_main_frag);
//        toolbar.setOnMenuItemClickListener(this);

        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setUserInputEnabled(false);

        bottomNavView.setOnNavigationItemSelectedListener(this);

        loggedInUserViewModel.getLoggedInUser().observe(this.getViewLifecycleOwner(), (LoggedInUser user)-> {
            if(user==null) {
                Toast.makeText(getContext(), "logged out", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.menu_home_activity_action_qrscan) {
            IntentIntegrator.forSupportFragment(this).initiateScan();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_home_bottom_nav_activity_home) {
            viewPager2.setCurrentItem(0);

            return true;

        } else if (itemId == R.id.action_history_bottom_nav_activity_home) {
            viewPager2.setCurrentItem(1);

            return true;

        } else if(itemId == R.id.action_profile_bottom_nav_activity_home) {
            viewPager2.setCurrentItem(2);

            return true;

        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(MainFragment.this.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String qrcode = result.getContents();
                Toast.makeText(MainFragment.this.getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                ApiClient.getInstance().getCycleIssuerClient().book(qrcode)
                        .enqueue(new Callback<BookedCycleResp>() {
                            @Override
                            public void onResponse(Call<BookedCycleResp> call, Response<BookedCycleResp> response) {
                                if(response.isSuccessful()) {
                                    BookedCycleResp res = response.body();
                                    if(res!=null) {
                                        if(response.code()==200) {
//                                            startLockActivity(res.getCycleId());
                                        } else {
                                            Toast.makeText(MainFragment.this.getContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
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

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        private static final int PAGE_NUM = 3;

        public ViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
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

                case 2: {
                    return new ProfileFragment();
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