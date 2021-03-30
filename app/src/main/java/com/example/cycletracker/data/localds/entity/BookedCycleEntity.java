package com.example.cycletracker.data.localds.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookedCycleEntity {
    @PrimaryKey
    @NonNull
    int cycleId;

    @ColumnInfo(name = "locked")
    boolean locked;

    public BookedCycleEntity(int cycleId, boolean locked) {
        this.cycleId = cycleId;
        this.locked = locked;
    }

    public int getCycleId() {
        return cycleId;
    }

    public boolean isLocked() {
        return locked;
    }
}
