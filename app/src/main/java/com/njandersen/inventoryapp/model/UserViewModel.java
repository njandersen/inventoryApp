package com.njandersen.inventoryapp.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.njandersen.inventoryapp.data.AppRepository;
import com.njandersen.inventoryapp.data.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    public static AppRepository repository;
    public final LiveData<List<User>> allUsers;

    //constructor
    public UserViewModel (Application application) {
        super(application);
        repository = new AppRepository((application));
        allUsers = repository.getAllUsersData();
    }

    public LiveData<List<User>> getAllUsers() {return allUsers;}
    public LiveData<User> getUser(int id) {return getUser(id);}

    public static void insertUser(User user) {repository.insertUser(user);}

    public static void updateUser(User user) {repository.updateUser(user);}

    public static void deleteUser(User user) {repository.deleteUser(user);}

}
