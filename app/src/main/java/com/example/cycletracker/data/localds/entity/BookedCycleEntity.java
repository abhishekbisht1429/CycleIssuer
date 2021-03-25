package com.example.cycletracker.data.localds.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookedCycleEntity {
    @PrimaryKey
    @NonNull
    int cycleId;

    public BookedCycleEntity(int cycleId) {
        this.cycleId = cycleId;
    }

    public int getCycleId() {
        return cycleId;
    }
}
