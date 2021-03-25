package com.example.cycletracker.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cycletracker.R;
import com.example.cycletracker.model.Bicycle;
import com.example.cycletracker.viewmodel.BicycleViewModel;
import com.example.cycletracker.viewmodel.LoggedInUserViewModel;

import javax.inject.Inject;

public class BicycleFragment extends PagerBaseFragment {

    @Inject
    BicycleViewModel bicycleViewModel;

    @Inject
    LoggedInUserViewModel loggedInUserViewModel;

    private TextView textViewCycleId;
    private Button buttonReturnCycle;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getPagerSubComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bicycle, container, false);
        textViewCycleId = view.findViewById(R.id.text_view_cycle_id);
        buttonReturnCycle = view.findViewById(R.id.btn_return_frag_bicycle);

        bicycleViewModel.getCycleLiveData().observe(this.getViewLifecycleOwner(), (Bicycle bicycle)-> {
            if(bicycle!=null) {
                textViewCycleId.setText("cycle id : "  + bicycle.getId()+"");
            }
        });
        buttonReturnCycle.setOnClickListener((View v)->{
            bicycleViewModel.returnCycle(bicycleViewModel.getCycleLiveData().getValue().getId(),
                    loggedInUserViewModel.getLoggedInUser().getValue().getAuthToken());
        });
        return view;
    }
}
