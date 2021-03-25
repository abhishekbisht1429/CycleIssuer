package com.example.cycletracker.home.dagger;

import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.home.dagger.pager.PagerSubComponent;
import com.example.cycletracker.home.fragment.HomeFragment;
import com.example.cycletracker.viewmodel.ViewModelFactory;

import java.util.concurrent.ExecutorService;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = PagerSubComponent.class)
public class HomeModule {

}
