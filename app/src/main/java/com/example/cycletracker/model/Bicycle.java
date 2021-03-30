package com.example.cycletracker.model;

public class Bicycle {

    private int id;
    private boolean locked;

    public Bicycle(int id, boolean locked) {
        this.id = id;
        this.locked = locked;
    }

    public int getId() {
        return id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLockState(boolean locked) {
        this.locked = locked;
    }

}
