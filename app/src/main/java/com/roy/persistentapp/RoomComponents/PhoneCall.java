package com.roy.persistentapp.RoomComponents;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "PhoneCall")
public class PhoneCall {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String duration;

    private String date;

    public PhoneCall(String duration, String date) {
        this.duration = duration;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public String getDate() {
        return date.toString();
    }

    @Override
    public String toString() {
        return "PhoneCall{" +
                ", duration='" + duration + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
