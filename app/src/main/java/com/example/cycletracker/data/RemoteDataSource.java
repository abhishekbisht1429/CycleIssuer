package com.example.cycletracker.data;

import com.example.cycletracker.data.localdb.LocalDatabase;
import com.example.cycletracker.data.localdb.UserDao;
import com.example.cycletracker.data.model.LoggedInUser;
import com.example.cycletracker.data.model.User;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.models.LoginRespData;
import com.example.cycletracker.retrofit.models.UserData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Class that handles authentication w/ fetchUserDetails credentials and retrieves user information.
 */
public class RemoteDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result;
        try {

            Response<LoginRespData> loginResponse = ApiClient.getInstance().getCycleIssuerClient().login(username, password).execute();
            if(loginResponse.isSuccessful()) {
                LoginRespData loginRespData = loginResponse.body();
                //Set auth token
                ApiClient.setAuthToken(loginRespData.getAuthToken());
                Response<UserData> userDataResponse = ApiClient.getInstance().getCycleIssuerClient().fetchUserDetails().execute();
                if(userDataResponse.isSuccessful()) {
                    UserData data = userDataResponse.body();
                    //TODO: retrive other fields as well from the loginResponseData
                    if(loginRespData!=null && data!=null) {
                        LoggedInUser user = new LoggedInUser(data.getUsername(), data.getFirstName() + " " + data.getLastName(), loginRespData.getAuthToken());
                        result = new Result.Success<LoggedInUser>(user);
                    } else {
                        result = new Result.Error(new Exception("Data is null. Code : "+loginResponse.code()+" "+userDataResponse.code()));
                    }
                } else {
                    result = new Result.Error(new Exception("Failed to retrieve user data after obtaining token. Error code : "+userDataResponse.code()));
                }
            } else {
                result = new Result.Error(new Exception("Failed to authenticated. Error code : "+loginResponse.code()));
            }
        } catch (IOException ioE) {
            result = new Result.Error(ioE);
        }
        return result;
    }

    public void logout(LoggedInUser user) {
        try {
            ApiClient.getInstance().getCycleIssuerClient().logout(user.getAuthToken()).execute();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}