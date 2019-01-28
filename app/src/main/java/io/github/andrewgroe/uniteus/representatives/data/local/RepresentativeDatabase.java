package io.github.andrewgroe.uniteus.representatives.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RepresentativeEntity.class, UserAddressEntity.class}, version = 1, exportSchema = false)
public abstract class RepresentativeDatabase extends RoomDatabase {
    public abstract RepresentativesDao representativesDao();

    public abstract UserAddressDao userAddressDao();
}
