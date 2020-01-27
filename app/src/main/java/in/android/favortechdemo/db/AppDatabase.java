package in.android.favortechdemo.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import in.android.favortechdemo.entities.ImageEntity;
import in.android.favortechdemo.entities.UserEntity;

@Database(entities = {UserEntity.class, ImageEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ImageDBDAO getTeacherDAO();

    public abstract UserDBDAO getUserDAO();
}
