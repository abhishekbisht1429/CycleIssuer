package com.example.cycletracker.data.localdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cycletracker.data.model.LoggedInUser;

@Dao
public interface UserDao {

    @Query("select * from loggedinuser")
    LoggedInUser findLoggedInUser();

    @Insert
    void saveLoggedInUser(LoggedInUser loggedInUser);

    @Delete
    void deleteLoggedInUser(LoggedInUser loggedInUser);
}
