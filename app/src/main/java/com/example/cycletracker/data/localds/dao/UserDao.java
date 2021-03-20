package com.example.cycletracker.data.localds.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cycletracker.data.localds.entity.LoggedInUserEntity;
import com.example.cycletracker.model.LoggedInUser;

@Dao
public interface UserDao {

    @Query("select * from loggedinuserentity")
    LoggedInUserEntity findLoggedInUser();

    @Insert
    void saveLoggedInUser(LoggedInUserEntity loggedInUserEntity);

    @Delete
    void deleteLoggedInUser(LoggedInUserEntity loggedInUserEntity);
}
