package com.example.cycletracker.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cycletracker.R;
import com.example.cycletracker.home.model.Bicycle;
import com.example.cycletracker.home.viewmodel.BicycleViewModel;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

public class HomeFragment extends HomeBaseFragment {

    private Button scanButton;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    @Inject
    BicycleViewModel bicycleViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        getHomeSubComponent().inject(this);
        super.onAttach(context);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        scanButton = view.findViewById(R.id.btn_scan_home_frag);
        scanButton.setOnClickListener((View v)-> {
            IntentIntegrator.forSupportFragment(this).initiateScan();
        });

        bicycleViewModel.getCycleLiveData().observe(this.getViewLifecycleOwner(), (Bicycle bicycle) -> {
            if(bicycle == null) {

            } else {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String qrcode = result.getContents();
                Toast.makeText(this.getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                LoggedInUser loggedInUser = loggedInUserViewModel.getLoggedInUser().getValue();
                if(loggedInUser!=null) {
                    bicycleViewModel.bookCycle(qrcode, loggedInUser.getAuthToken());
                } else {
                    Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}