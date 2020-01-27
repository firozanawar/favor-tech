package in.android.favortechdemo.di.module;


import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.android.favortechdemo.db.AppDatabase;
import in.android.favortechdemo.db.ImageDBDAO;
import in.android.favortechdemo.db.UserDBDAO;


@Module
public class DBModule {

    private final String DB_NAME = "db-user-ilma";

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
    }

    @Provides
    UserDBDAO provideUserDAO(AppDatabase appDatabase) {
        return appDatabase.getUserDAO();
    }

    @Provides
    ImageDBDAO provideTeacherDAO(AppDatabase appDatabase) {
        return appDatabase.getTeacherDAO();
    }
}
