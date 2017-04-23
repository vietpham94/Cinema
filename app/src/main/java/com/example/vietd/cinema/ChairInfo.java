package com.example.vietd.cinema;

/**
 * Created by Optimus on 4/23/2017.
 */

public class ChairInfo {
    String seat;
    int type;

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ChairInfo(String seat, int type) {

        this.seat = seat;
        this.type = type;
    }
}
