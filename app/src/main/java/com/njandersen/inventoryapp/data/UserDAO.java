package com.njandersen.inventoryapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User ORDER BY last_name ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM User WHERE User.user_name == :userName AND User.password == :password")
    LiveData<User> getUser(String userName, String password);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);


}
