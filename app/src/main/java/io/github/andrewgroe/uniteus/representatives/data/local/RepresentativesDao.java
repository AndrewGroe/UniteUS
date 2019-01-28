package io.github.andrewgroe.uniteus.representatives.data.local;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface RepresentativesDao {
    @Query("SELECT * FROM reps")
    Single<List<RepresentativeEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReps(List<RepresentativeEntity> reps);

    @Query("SELECT count(*) FROM reps")
    Single<Integer> getCount();

    @Query("DELETE FROM reps")
    void clearTable();

}
