package com.example.cycletracker.data;

import com.example.cycletracker.data.localds.LocalDataSource;
import com.example.cycletracker.data.localds.entity.LoggedInUserEntity;
import com.example.cycletracker.model.Result;
import com.example.cycletracker.data.remoteds.RemoteDataSource;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.retrofit.ApiClient;

import javax.inject.Inject;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of fetchUserDetails status and user credentials information.
 */
public class DataRepository {

    private static final String USERNAME_KEY = "user name preference key";
    private static final String AUTH_TOKEN_KEY = "auth token preference key";

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    @Inject
    public DataRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public Result<String> logout(LoggedInUser loggedInUser) {
        Result<String> res = remoteDataSource.logout(loggedInUser);

        if(res instanceof Result.Success) {
            LoggedInUserEntity loggedInUserEntity = new LoggedInUserEntity(loggedInUser.getUsername(),
                    loggedInUser.getDisplayName(),
                    loggedInUser.getAuthToken());
            localDataSource.getUserDao().deleteLoggedInUser(loggedInUserEntity);
        }

        return res;
    }


    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result = remoteDataSource.login(username, password);
        if (result instanceof Result.Success) {
            LoggedInUser loggedInUser = ((Result.Success<LoggedInUser>)result).getData();

            LoggedInUserEntity loggedInUserEntity = new LoggedInUserEntity(loggedInUser.getUsername(),
                    loggedInUser.getDisplayName(),
                    loggedInUser.getAuthToken());
            localDataSource.getUserDao().saveLoggedInUser(loggedInUserEntity);
        }
        return result;
    }

    public Result<LoggedInUser> findLoggedInUser() {
        LoggedInUserEntity userEntity = localDataSource.getUserDao().findLoggedInUser();
        if(userEntity!=null) {
            LoggedInUser loggedInUser = new LoggedInUser(userEntity.getUsername(),
                    userEntity.getDisplayName(),
                    userEntity.getAuthToken());
            return new Result.Success<LoggedInUser>(loggedInUser);
        } else {
            return new Result.Error(new Exception("No Logged in user found"));
        }
    }

    public void lock(int cycleId, int lockVal) {
        remoteDataSource.lock(cycleId, lockVal);
    }
}
