package com.example.cycletracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cycletracker.dagger.activity.ActivityScope;
import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.data.localds.entity.BookedCycleEntity;
import com.example.cycletracker.model.Bicycle;
import com.example.cycletracker.model.Result;

import java.util.concurrent.ExecutionException;
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
            executorService.submit(() -> {
                try {
                    Result<Bicycle> res = dataRepository.bookCycle(qrcode, authToken);
                    if (res instanceof Result.Success) {
                        cycleMutableLiveData.postValue(((Result.Success<Bicycle>) res).getData());
                    } else {
                        cycleMutableLiveData.postValue(null);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

    }

    public void returnCycle(String authToken) {
            executorService.submit(() -> {
                try {
                    Result<String> res = dataRepository.returnCycle(cycleMutableLiveData.getValue(), authToken);
                    if (res instanceof Result.Success) {
                        cycleMutableLiveData.postValue(null);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
    }

    public void findBookedCycle() {
            executorService.submit(() -> {
                Result<Bicycle> res = dataRepository.findBookedCycle();
                if(res instanceof Result.Success) {

                    Bicycle bicycle = ((Result.Success<Bicycle>) res).getData();
                    cycleMutableLiveData.postValue(bicycle);
                } else {
                    cycleMutableLiveData.postValue(null);
                }
            });
    }

    public void changeLockState(boolean locked, String authToken) {
        executorService.submit(()-> {
            Result<String> res = dataRepository.changeLockState(cycleMutableLiveData.getValue(), locked, authToken);
            if(res instanceof Result.Success) {
                cycleMutableLiveData.getValue().setLockState(locked);
            } else {
                /* Set the error flag */
            }
        });
    }
}
