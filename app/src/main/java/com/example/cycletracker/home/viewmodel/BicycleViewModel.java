package com.example.cycletracker.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.home.dagger.HomeScope;
import com.example.cycletracker.home.model.Cycle;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

@HomeScope
public class BicycleViewModel extends ViewModel {

    private DataRepository dataRepository;
    private ExecutorService executorService;

    MutableLiveData<Cycle> cycleMutableLiveData = new MutableLiveData<>();

    @Inject
    BicycleViewModel(DataRepository dataRepository, ExecutorService executorService) {
        this.dataRepository = dataRepository;
        this.executorService = executorService;
    }

    public LiveData<Cycle> getCycleLiveData() {
        return cycleMutableLiveData;
    }
}
