package com.example.cycletracker.data;

import com.example.cycletracker.data.model.LoggedInUser;
import com.example.cycletracker.data.model.User;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.models.LoginResp;
import com.example.cycletracker.retrofit.models.UserData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Class that handles authentication w/ fetchUserDetails credentials and retrieves user information.
 */
public class LoginDataSource {

    private static List<User> users;
    static {
        users = new ArrayList<>();
        users.add(new User("500060150","123456"));
        users.add(new User("500000001","123456"));
        users.add(new User("500000002","123456"));
        users.add(new User("500000003","123456"));
    }
    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result;
        try {

            Response<LoginResp> loginResponse = ApiClient.getInstance().getCycleIssuerClient().login(username, password).execute();
            if(loginResponse.isSuccessful()) {
                Response<UserData> userDataResponse = ApiClient.getInstance().getCycleIssuerClient().fetchUserDetails(username, password).execute();
                if(userDataResponse.isSuccessful()) {
                    UserData data = userDataResponse.body();
                    //TODO: retrive other fields as well from the loginResponseData
                    LoggedInUser user = new LoggedInUser(data.getUsername(), data.getFirstName() + " " + data.getLastName());
                    result = new Result.Success<LoggedInUser>(user);
                } else {
                    result = new Result.Error(new Exception("Failed to retrieve use data after obtaining token. Error code : "+userDataResponse.code()));
                }
            } else {
                result = new Result.Error(new Exception("Failed to authenticated. Error code : "+loginResponse.code()));
            }
        } catch (IOException ioE) {
            result = new Result.Error(ioE);
        }
        return result;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
