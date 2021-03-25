package com.example.cycletracker.data.localds;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cycletracker.data.localds.dao.BicycleDao;
import com.example.cycletracker.data.localds.dao.UserDao;
import com.example.cycletracker.data.localds.entity.BookedCycleEntity;
import com.example.cycletracker.data.localds.entity.LoggedInUserEntity;
import com.example.cycletracker.model.Bicycle;
import com.example.cycletracker.model.LoggedInUser;

@Database(entities = {LoggedInUserEntity.class, BookedCycleEntity.class}, version = 1)
public abstract class LocalDataSource extends RoomDatabase {

    public abstract UserDao getUserDao();

    public abstract BicycleDao getBicycleDao();
}