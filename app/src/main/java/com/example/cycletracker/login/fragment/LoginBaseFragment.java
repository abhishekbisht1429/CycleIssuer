package com.example.cycletracker.login.fragment;

import androidx.fragment.app.Fragment;

import com.example.cycletracker.MyApplication;
import com.example.cycletracker.home.dagger.HomeSubComponent;
import com.example.cycletracker.login.dagger.LoginSubcomponent;

public class LoginBaseFragment extends Fragment {
    LoginSubcomponent getLoginSubComponent() {
        return ((MyApplication) requireActivity().getApplication()).getAppComponent()
                .loginSubcomponentBuilder()
                .build();
    }
}
