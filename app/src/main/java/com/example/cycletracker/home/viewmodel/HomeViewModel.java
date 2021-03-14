package com.example.cycletracker.home.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cycletracker.data.DataRepository;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Boolean> loginState = new MutableLiveData<>();

    private DataRepository dataRepository;
    public HomeViewModel(Application application) {
        dataRepository = DataRepository.getInstance(application);
    }

    public MutableLiveData<Boolean> getLoginState() {
        return loginState;
    }

    public void logout() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dataRepository.logout();
                return null;
            }
        }.execute();
    }

    public void loginStateChanged(boolean state) {
        loginState.setValue(state);
    }
}
