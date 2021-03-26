package com.example.cycletracker.login.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cycletracker.R;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;

import javax.inject.Inject;

public class SplashFragment extends LoginBaseFragment {

    private static final long SPLASH_TIME_OUT = 3000;
    private NavController navController;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        getLoginSubComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        loggedInUserViewModel.getLoggedInUser().observe(this.getViewLifecycleOwner(), (LoggedInUser loggedInUser) -> {
            new Handler().postDelayed(()-> {
                CharSequence name = navController.getCurrentDestination().getLabel();
                Log.i("name : ", name.toString());
                if(loggedInUser == null) {
                    navController.navigate(R.id.action_splashFragment_to_loginFragment);
                } else {
                    navController.navigate(R.id.action_splashFragment_to_homeFragment);
                }
            }, SPLASH_TIME_OUT);
        });
        loggedInUserViewModel.findLoggedInUser(); //find already logged in user if any

        return view;
    }
}
