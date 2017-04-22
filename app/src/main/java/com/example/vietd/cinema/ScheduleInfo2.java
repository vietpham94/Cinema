package com.example.vietd.cinema;

/**
 * Created by Optimus on 4/20/2017.
 */

public class ScheduleInfo2 {
    int id;
    String time;
    String date;
    int room;

    public ScheduleInfo2(int id, String time, String date, int room) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.room = room;

    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}