package com.example.cycletracker.dagger.activity;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import com.example.cycletracker.activity.MainActivity;
import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.home.dagger.HomeSubcomponent;
import com.example.cycletracker.home.dagger.pager.PagerFragmentScope;
import com.example.cycletracker.home.fragment.HomeFragment;
import com.example.cycletracker.viewmodel.BicycleViewModel;
import com.example.cycletracker.viewmodel.ViewModelFactory;

import java.util.concurrent.ExecutorService;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

@Module(subcomponents = HomeSubcomponent.class)
public class ActivityModule {

    private MainActivity mainActivity;
    public ActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @ActivityScope
    MainActivity mainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    BicycleViewModel provideBicycleViewModel(MainActivity activity, ViewModelFactory viewModelFactory) {
        return new ViewModelProvider(activity.getViewModelStore(), viewModelFactory).get(BicycleViewModel.class);
    }
}