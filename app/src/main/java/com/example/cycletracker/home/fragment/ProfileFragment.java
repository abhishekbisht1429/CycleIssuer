package com.example.cycletracker.home.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;

import javax.inject.Inject;

public class ProfileFragment extends PagerBaseFragment {

    private Button logoutButton;
    private TextView textView;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getPagerSubComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        loggedInUserViewModel.getLoggedInUser().observe(this.getViewLifecycleOwner(), (LoggedInUser loggedInUser) -> {
            if(loggedInUser == null) {
                Toast.makeText(this.getContext(), "Successfully Logged out", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else {
                Toast.makeText(this.getContext(), "Failed to logout", Toast.LENGTH_SHORT).show();
            }
        });
        logoutButton = view.findViewById(R.id.btn_logout_profile_frag);
        textView = view.findViewById(R.id.text_view_name_profile_frag);

        logoutButton.setOnClickListener((View v) -> {
            loggedInUserViewModel.logout();
        });

        return view;
    }

    public interface FragmentStateChangeListener {
        void onClickLogout();
    }
}