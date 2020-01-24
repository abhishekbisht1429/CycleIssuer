package com.example.cycletracker.data.localdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cycletracker.data.model.LoggedInUser;
import com.example.cycletracker.data.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LoggedInUser.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    private static volatile LocalDatabase database;

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService exService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static LocalDatabase getInstance(Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context, LocalDatabase.class, "Local Database").build();
        }
        return  database;
    }
    public abstract UserDao getUserDao();
}