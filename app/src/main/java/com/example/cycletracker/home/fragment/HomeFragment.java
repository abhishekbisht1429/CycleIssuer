package com.example.cycletracker.home.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.cycletracker.R;
import com.example.cycletracker.activity.MainActivity;
import com.example.cycletracker.home.dagger.HomeSubcomponent;
import com.example.cycletracker.model.Bicycle;
import com.example.cycletracker.viewmodel.BicycleViewModel;
import com.example.cycletracker.viewmodel.ViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ViewPager2 viewPager2;
    private CustomAdapter pagerAdapter;
    private BottomNavigationView bottomNavView;

    private HomeSubcomponent homeSubcomponent;

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    BicycleViewModel bicycleViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((MainActivity)requireActivity()).getActivitySubcomponent()
                .homeSubComponentBuilder()
                .build()
                .inject(this);
        super.onAttach(context);
    }

    public HomeSubcomponent getHomeSubcomponent() {
        return homeSubcomponent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        pagerAdapter = new CustomAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = view.findViewById(R.id.toolbar_home_activity);
        viewPager2 = view.findViewById(R.id.viewpager_home_activity);
        bottomNavView = view.findViewById(R.id.bottom_nav_view_home_activity);

//        toolbar.inflateMenu(R.menu.menu_toolbar_main_frag);
//        toolbar.setOnMenuItemClickListener(this);

        toolbar.setTitle(R.string.app_name);

        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setUserInputEnabled(false);

        bicycleViewModel.getCycleLiveData().observe(this.getViewLifecycleOwner(), (Bicycle bicycle)->{
            pagerAdapter.notifyDataSetChanged();
        });

        bicycleViewModel.findBookedCycle();

        bottomNavView.setOnNavigationItemSelectedListener(this);

        return view;
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

    private class CustomFragmentViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout frameLayout;
        private FragmentContainerView fragmentContainerView;
        private FragmentManager fragmentManager;
        public CustomFragmentViewHolder(@NonNull View itemView, FragmentManager fragmentManager) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_layout_view_holder);
            fragmentContainerView = new FragmentContainerView(HomeFragment.this.getContext());
            fragmentContainerView.setId(View.generateViewId());
            frameLayout.addView(fragmentContainerView);
            this.fragmentManager = fragmentManager;
        }

        public void bindView(Fragment fragment) {
//            Fragment fragment1 = fragmentManager.findFragmentById(fragmentContainerView.getId());
//            if(fragment1 == null) {
//                fragmentManager.beginTransaction()
//                        .add(fragmentContainerView.getId(), fragment)
//                        .commit();
//            } else {
                fragmentManager.beginTransaction()
                        .replace(fragmentContainerView.getId(), fragment)
                        .commit();
//            }
        }
    }



    private class CustomAdapter extends RecyclerView.Adapter<CustomFragmentViewHolder> {
        private final int ITEM_COUNT = 3;

        private Fragment getFragment(long itemId) {

            if(itemId == R.layout.fragment_scan_prompt) {
                return new ScanPromptFragment();
            } else if(itemId == R.layout.fragment_bicycle) {
                return new BicycleFragment();
            } else if(itemId == R.layout.fragment_history) {
                return new HistoryFragment();
            } else if(itemId == R.layout.fragment_profile) {
                return new ProfileFragment();
            } else {
                throw new IllegalArgumentException("Invalid layout id");
            }
        }

        @NonNull
        @Override
        public CustomFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_holder, parent, false);
            return new CustomFragmentViewHolder(view, HomeFragment.this.getChildFragmentManager());
        }

        @Override
        public void onBindViewHolder(@NonNull CustomFragmentViewHolder holder, int position) {
            holder.bindView(getFragment(getItemId(position)));
        }

        @Override
        public int getItemCount() {
            return ITEM_COUNT;
        }

        @Override
        public long getItemId(int position) {
            switch (position) {
                case 0 : {
                    if(HomeFragment.this.bicycleViewModel.getCycleLiveData().getValue() == null) {
                        return R.layout.fragment_scan_prompt;
                    } else {
                        return R.layout.fragment_bicycle;
                    }
                }
                case 1 : {
                    return R.layout.fragment_history;
                }
                case 2 : {
                    return R.layout.fragment_profile;
                }
                default : {
                    throw new IllegalArgumentException("Till now only 3 fragments allowed");
                }
            }
        }
    }
}