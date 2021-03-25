package com.example.cycletracker.home.dagger.pager;


import com.example.cycletracker.home.fragment.BicycleFragment;
import com.example.cycletracker.home.fragment.HistoryFragment;
import com.example.cycletracker.home.fragment.ProfileFragment;
import com.example.cycletracker.home.fragment.ScanPromptFragment;
import com.example.cycletracker.model.Bicycle;

import dagger.Subcomponent;

@PagerFragmentScope
@Subcomponent(modules = PagerModule.class)
public interface PagerSubComponent {

    @Subcomponent.Builder
    public interface Builder {
        PagerSubComponent build();
    }

    void inject(ProfileFragment profileFragment);
    void inject(ScanPromptFragment scanPromptFragment);
    void inject(HistoryFragment historyFragment);
    void inject(BicycleFragment bicycleFragment);
}
