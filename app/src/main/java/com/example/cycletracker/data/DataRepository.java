package com.example.cycletracker.data;

import android.app.Application;

import com.example.cycletracker.data.localdb.LocalDatabase;
import com.example.cycletracker.data.model.LoggedInUser;
import com.example.cycletracker.retrofit.ApiClient;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of fetchUserDetails status and user credentials information.
 */
public class DataRepository {

    private static final String USERNAME_KEY = "user name preference key";
    private static final String AUTH_TOKEN_KEY = "auth token preference key";

    private static volatile DataRepository instance;

    private RemoteDataSource dataSource;
    private LocalDatabase database;

    private LoggedInUser user = null;

    // private constructor : singleton access
    private DataRepository(Application application) {
        dataSource = new RemoteDataSource();
        database = LocalDatabase.getInstance(application);
    }

    public static DataRepository getInstance(Application application) {
        if (instance == null) {
            instance = new DataRepository(application);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        dataSource.logout(user);
        database.getUserDao().deleteLoggedInUser(user);
        user = null;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
            database.getUserDao().saveLoggedInUser(user);
        }
        return result;
    }

    public Result<LoggedInUser> findLoggedInUser() {
        LoggedInUser user = database.getUserDao().findLoggedInUser();
        if(user!=null) {
            //Set auth token
            ApiClient.setAuthToken("Token "+user.getAuthToken());
            setLoggedInUser(user);
            return new Result.Success<LoggedInUser>(user);
        } else {
            return new Result.Error(new Exception("No Logged in user found"));
        }
    }

    public void lock(int cycleId, int lockVal) {
        dataSource.lock(cycleId, lockVal);
    }
}
