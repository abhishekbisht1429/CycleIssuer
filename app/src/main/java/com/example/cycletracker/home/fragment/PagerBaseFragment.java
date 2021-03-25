package com.example.cycletracker.home.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cycletracker.activity.MainActivity;
import com.example.cycletracker.home.dagger.pager.PagerSubComponent;

public class PagerBaseFragment extends Fragment {

    private PagerSubComponent pagerSubComponent;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pagerSubComponent = ((MainActivity) requireActivity()).getActivitySubcomponent()
                .homeSubComponentBuilder()
                .build()
                .pagerSubComponentBuilder()
                .build();
    }

    PagerSubComponent getPagerSubComponent() {
        return pagerSubComponent;
    }
}
