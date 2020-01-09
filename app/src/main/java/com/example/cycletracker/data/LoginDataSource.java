package com.example.cycletracker.data;

import com.example.cycletracker.data.model.LoggedInUser;
import com.example.cycletracker.data.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static List<User> users;
    static {
        users = new ArrayList<>();
        users.add(new User("500060150","1234"));
        users.add(new User("500000001","1234"));
        users.add(new User("500000002","1234"));
        users.add(new User("500000003","1234"));
    }
    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            for(User user : users) {
                if(username.equals(user.getSapId()) && password.equals(user.getPassword())) {
                    LoggedInUser loggedInUser = new LoggedInUser(username, user.getSapId());
                    return new Result.Success<>(loggedInUser);
                }
            }
            return new Result.Error(new IllegalArgumentException("Invalid user credentials"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
