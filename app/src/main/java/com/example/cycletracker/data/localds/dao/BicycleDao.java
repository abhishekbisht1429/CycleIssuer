package com.example.cycletracker.data.localds.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cycletracker.data.localds.entity.BookedCycleEntity;
import com.example.cycletracker.model.Bicycle;

@Dao
public interface BicycleDao {
    @Query("select * from bookedcycleentity")
    BookedCycleEntity findBookedCycle();

    @Insert
    void saveBookedCycle(BookedCycleEntity bookedCycleEntity);

    @Delete
    void deleteBookedCycle(BookedCycleEntity bookedCycleEntity);

    @Update
    void updateBookedCycle(BookedCycleEntity bookedCycleEntity);
}
