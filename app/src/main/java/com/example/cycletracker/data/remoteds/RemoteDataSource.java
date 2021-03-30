package com.example.cycletracker.data.remoteds;

import com.example.cycletracker.model.Bicycle;
import com.example.cycletracker.model.Result;
import com.example.cycletracker.model.LoggedInUser;
import com.example.cycletracker.retrofit.ApiClient;
import com.example.cycletracker.retrofit.model.BookedCycleResp;
import com.example.cycletracker.retrofit.model.GenericResponse;
import com.example.cycletracker.retrofit.model.LoginRespData;
import com.example.cycletracker.retrofit.model.UserData;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Class that handles authentication w/ fetchUserDetails credentials and retrieves user information.
 */
public class RemoteDataSource {
    
    private ApiClient apiClient;
    
    @Inject
    public RemoteDataSource(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result;
        try {
            Response<LoginRespData> loginResponse = apiClient.getCycleIssuerClient().login(username, password).execute();
            if(loginResponse.isSuccessful()) {
                LoginRespData loginRespData = loginResponse.body();
                //Set auth token
//                ApiClient.setAuthToken("Token "+loginRespData.getAuthToken());
                String authToken = "Token "+loginRespData.getAuthToken();
                Response<UserData> userDataResponse = apiClient.getCycleIssuerClient().fetchUserDetails(authToken).execute();
                if(userDataResponse.isSuccessful()) {
                    UserData data = userDataResponse.body();
                    //TODO: retrive other fields as well from the loginResponseData
                    if(loginRespData!=null && data!=null) {
                        LoggedInUser user = new LoggedInUser(data.getUsername(), data.getFirstName() + " " + data.getLastName(), authToken);
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

    public Result<String> logout(LoggedInUser user) {
        Result<String> result;
        try {
            Response<String> response = apiClient.getCycleIssuerClient().logout(user.getAuthToken()).execute();
            if(response.isSuccessful()) {
                result = new Result.Success<String>(response.body());
            } else {
                result = new Result.Error(new Exception("Failed to logout. Error code : "+response.code()));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            result = new Result.Error(ioe);
        }

        return result;
    }

    public Result<Bicycle> bookCycle(String qrcode, String authToken) {
        Result<Bicycle> res;
        try {
            Response<BookedCycleResp> response = apiClient.getCycleIssuerClient().book(qrcode, authToken).execute();
            if(response.isSuccessful()) {
                BookedCycleResp bookedCycleResp = response.body();
                Bicycle bicycle = new Bicycle(bookedCycleResp.getCycleId(), bookedCycleResp.isLocked());
                res = new Result.Success<Bicycle>(bicycle);
            } else {
                res = new Result.Error(new Exception("Failed to book cycle. Error code : "+response.code()));
            }
        } catch(IOException ioe) {
            res = new Result.Error(ioe);
        }
        return res;
    }

    public Result<String> changeLockState(int cycleId, boolean locked, String authToken) {
        Result<String> res;
        try {
            Response<GenericResponse> response = apiClient.getCycleIssuerClient().lock(cycleId, locked, authToken).execute();
            if(response.isSuccessful()) {
                res = new Result.Success<String>(response.body().getMessage());
            } else {
                res = new Result.Error(new Exception("Failed to change the lock state. Error code : "+response.code()));
            }
        } catch (IOException ioe) {
            res = new Result.Error(ioe);
            ioe.printStackTrace();
        } catch (Exception e) {
            res = new Result.Error(e);
        }
        return res;
    }

    public Result<String> returnCycle(int cycleId, String authToken) {
        Result<String> res;
        try {
            Response<GenericResponse> response = apiClient.getCycleIssuerClient().returnCycle(cycleId, authToken).execute();
            if(response.isSuccessful()) {
                res = new Result.Success<String>(response.body().getMessage());
            } else {
                res = new Result.Error(new Exception("Failed to return cycle. Error code : "+response.code()));
            }
        } catch (IOException ioe) {
            res = new Result.Error(ioe);
        } catch (Exception e) {
            res = new Result.Error(e);
        }
        return res;
    }
}