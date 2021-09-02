package co.com.ceiba.mobile.pruebadeingreso.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.com.ceiba.mobile.pruebadeingreso.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "users_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
