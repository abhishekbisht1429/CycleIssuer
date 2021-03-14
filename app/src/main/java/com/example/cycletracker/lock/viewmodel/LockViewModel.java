package com.example.cycletracker.lock.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cycletracker.data.DataRepository;

public class LockViewModel extends ViewModel {
    private MutableLiveData<Boolean> switchState = new MutableLiveData<>();
    private MutableLiveData<Boolean> bookingState = new MutableLiveData<>();
    private int cycleId;
    private DataRepository dataRepository;
    public LockViewModel(Application application) {
        dataRepository = DataRepository.getInstance(application);
    }

    public MutableLiveData<Boolean> getSwitchState() {
        return switchState;
    }

    private void lock(int cycleId, int lockVal) {
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                int cycleId = integers[0];
                int lockVal = integers[1];
                dataRepository.lock(cycleId, lockVal);
                return null;
            }
        }.execute(cycleId, lockVal);
    }

    public void lockStateChaged(int cycleId, boolean state) {
        switchState.setValue(state);
        lock(cycleId, state?1:0);
    }
}
