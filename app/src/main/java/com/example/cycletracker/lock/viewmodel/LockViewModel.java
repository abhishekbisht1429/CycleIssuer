package com.example.cycletracker.lock.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cycletracker.data.DataRepository;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class LockViewModel extends ViewModel {
    private MutableLiveData<Boolean> switchState = new MutableLiveData<>();
    private MutableLiveData<Boolean> bookingState = new MutableLiveData<>();
    private int cycleId;
    private DataRepository dataRepository;
    private ExecutorService executorService;

    @Inject
    public LockViewModel(DataRepository dataRepository, ExecutorService executorService) {
        this.dataRepository = dataRepository;
        this.executorService = executorService;
    }

    public MutableLiveData<Boolean> getSwitchState() {
        return switchState;
    }

    private void lock(int cycleId, int lockVal) {
        executorService.submit(()-> {
            dataRepository.lock(cycleId, lockVal);
        });
    }

    public void lockStateChaged(int cycleId, boolean state) {
        switchState.setValue(state);
        lock(cycleId, state?1:0);
    }
}
