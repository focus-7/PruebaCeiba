package co.com.ceiba.mobile.pruebadeingreso.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.entities.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsers(List<User> users);
}
