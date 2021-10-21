package com.roy.persistentapp.RoomComponents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * for accessing database
 */
@Dao
public interface PhoneCallDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PhoneCall call);

    @Query("Select * from PhoneCall")
    LiveData<List<PhoneCall>> getAll();
}
