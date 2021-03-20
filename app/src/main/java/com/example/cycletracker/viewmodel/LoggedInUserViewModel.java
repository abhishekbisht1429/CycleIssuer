package com.example.cycletracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cycletracker.dagger.ApplicationScope;
import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.model.Result;
import com.example.cycletracker.model.LoggedInUser;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

@ApplicationScope
public class LoggedInUserViewModel extends ViewModel {

    MutableLiveData<LoggedInUser> loggedInUser = new MutableLiveData<>();

    Result lastOpRes;

    private final DataRepository dataRepository;
    private final ExecutorService executorService;

    @Inject
    public LoggedInUserViewModel(DataRepository dataRepository, ExecutorService executorService) {
        this.dataRepository = dataRepository;
        this.executorService = executorService;
    }

    public void findLoggedInUser() {
        executorService.submit(() -> {
            Result<LoggedInUser> res = dataRepository.findLoggedInUser();
            if(res instanceof Result.Success) {
                LoggedInUser user = ((Result.Success<LoggedInUser>)res).getData();
                loggedInUser.postValue(user);
            } else {
                loggedInUser.postValue(null);
            }
        });
    }

    public void login(String username, String password) {
        executorService.submit(()-> {
           Result<LoggedInUser> res = dataRepository.login(username, password);
            if(res instanceof Result.Success) {
                LoggedInUser user = ((Result.Success<LoggedInUser>)res).getData();
                loggedInUser.postValue(user);
            } else {
                loggedInUser.postValue(null);
            }
        });
    }

    public void logout() {
        executorService.submit(()->{
            Result<String> res = dataRepository.logout(loggedInUser.getValue());
            if(res instanceof Result.Success) {
                loggedInUser.postValue(null);
            }
        });
    }

    public LiveData<LoggedInUser> getLoggedInUser() {
        return loggedInUser;
    }

    public Result getLastOpRes() {
        return lastOpRes;
    }
}
