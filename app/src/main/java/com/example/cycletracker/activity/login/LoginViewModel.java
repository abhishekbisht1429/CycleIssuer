package com.example.cycletracker.activity.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Patterns;

import com.example.cycletracker.data.DataRepository;
import com.example.cycletracker.data.Result;
import com.example.cycletracker.data.model.LoggedInUser;
import com.example.cycletracker.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private DataRepository dataRepository;

    public LoginViewModel(Application application) {
        this.dataRepository = DataRepository.getInstance(application);
        findLoggedInUser();
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        //Since the outer class is not an Activity or Fragment, therefore no need to worry about memory leak
        @SuppressLint("StaticFieldLeak") AsyncTask loginTaks = new AsyncTask<String, Void, Result<LoggedInUser>>() {

            @Override
            protected Result<LoggedInUser> doInBackground(String... strings) {
                String username = strings[0];
                String password = strings[1];

                Result<LoggedInUser> result = dataRepository.login(username, password);
                return result;
            }

            @Override
            protected void onPostExecute(Result<LoggedInUser> result) {
                setLoginResult(result);
            }
        }.execute(username, password);

    }


    //TODO: this is jst for testing. Actual logut wont be part of login activity
    public void logout() {
        @SuppressLint("StaticFieldLeak") AsyncTask loginTaks = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... strings) {
                dataRepository.logout();
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {

            }
        }.execute();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }


    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 3;
    }

    private void setLoginResult(Result<LoggedInUser> result) {
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    private void findLoggedInUser() {
        //Since the outer class is not an Activity or Fragment, therefore no need to worry about memory leak
        @SuppressLint("StaticFieldLeak") AsyncTask loginTaks = new AsyncTask<Void, Void, Result<LoggedInUser>>() {

            @Override
            protected Result<LoggedInUser> doInBackground(Void... strings) {
                Result<LoggedInUser> result = dataRepository.findLoggedInUser();
                return result;
            }

            @Override
            protected void onPostExecute(Result<LoggedInUser> result) {
                setLoginResult(result);
            }
        }.execute();
    }
}
