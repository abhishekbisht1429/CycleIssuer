package com.example.cycletracker.home.fragment;

import androidx.fragment.app.Fragment;

import com.example.cycletracker.MyApplication;
import com.example.cycletracker.home.dagger.HomeSubComponent;

import java.util.Objects;

public class HomeBaseFragment extends Fragment {

    HomeSubComponent getHomeSubComponent() {
        return ((MyApplication) requireActivity().getApplication()).getAppComponent()
                .homeSubComponentBuilder()
                .build();
    }
}
