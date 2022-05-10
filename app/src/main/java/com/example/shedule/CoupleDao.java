package com.example.shedule;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoupleDao {
    @Query("SELECT * FROM couple")
    LiveData<List<Couple>> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Couple couple);
    @Update
    void update(Couple couple);
    @Delete
    void delete(Couple couple);
    @Query("DELETE FROM couple")
    void deleteAll();
}
