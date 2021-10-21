package com.roy.persistentapp.RoomComponents;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = PhoneCall.class, version = 1)
public abstract class PhoneCallDatabase extends RoomDatabase {
    public abstract PhoneCallDao phoneCallDao();

    public static volatile PhoneCallDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //synchronized -> only 1 thread can access this method, hence only 1 instance will exits
    public static synchronized PhoneCallDatabase getInstance(Context context) {
        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PhoneCallDatabase.class, "phonecall_database")//for db filename
                    .fallbackToDestructiveMigration() //when db version update
                    .build();

        }
        return INSTANCE;
    }
}
