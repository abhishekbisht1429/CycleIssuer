package com.example.cycletracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cycletracker.dagger.activity.ActivityScope;
import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.model.Bicycle;
import com.example.cycletracker.model.Result;

import java.util.concurrent.ExecutorService;

@ActivityScope
public class BicycleViewModel extends ViewModel {

    private DataRepository dataRepository;
    private ExecutorService executorService;

    MutableLiveData<Bicycle> cycleMutableLiveData = new MutableLiveData<>();

    BicycleViewModel(DataRepository dataRepository, ExecutorService executorService) {
        this.dataRepository = dataRepository;
        this.executorService = executorService;
    }

    public LiveData<Bicycle> getCycleLiveData() {
        return cycleMutableLiveData;
    }

    public void bookCycle(String qrcode, String authToken) {
        executorService.submit(()->{
            Result<Bicycle> res = dataRepository.bookCycle(qrcode, authToken);
            if(res instanceof Result.Success) {
                cycleMutableLiveData.postValue(((Result.Success<Bicycle>) res).getData());
            } else {
                cycleMutableLiveData.postValue(null);
            }
        });
    }

    public void returnCycle(int id, String authToken) {
        executorService.submit(()-> {
            Result<String> res = dataRepository.returnCycle(id, authToken);
            if(res instanceof Result.Success) {
                cycleMutableLiveData.postValue(null);
            }
        });
    }
}
