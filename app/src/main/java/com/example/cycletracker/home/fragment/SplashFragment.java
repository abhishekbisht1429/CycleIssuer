package com.example.cycletracker.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cycletracker.MyApplication;
import com.example.cycletracker.R;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.util.Utility;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;

import javax.inject.Inject;

public class SplashFragment extends HomeBaseFragment {

    private static final long SPLASH_TIME_OUT = 3000;
    private NavController navController;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        getHomeSubComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        loggedInUserViewModel.getLoggedInUser().observe(this, (LoggedInUser loggedInUser) -> {
            new Handler().postDelayed(()-> {
                if(loggedInUser == null) {
                    navController.navigate(R.id.action_splashFragment_to_loginFragment);
                } else {
                    navController.navigate(R.id.action_splashFragment_to_mainFragment3);
                }
            }, SPLASH_TIME_OUT);
        });
        loggedInUserViewModel.findLoggedInUser(); //find already logged in user if any
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        return view;
    }
}
