package io.github.andrewgroe.uniteus.representatives.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface UserAddressDao {
    @Query("SELECT * FROM user_address")
    Single<UserAddressEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserAddress(UserAddressEntity address);

    @Query("SELECT count(*) FROM user_address")
    Single<Integer> getCount();

    @Query("DELETE FROM user_address")
    void clearTable();
}
