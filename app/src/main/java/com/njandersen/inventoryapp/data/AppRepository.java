package com.njandersen.inventoryapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private InventoryDAO inventoryDAO;
    private UserDAO userDAO;
    private LiveData<List<Inventory>> allItems;
    private LiveData<List<User>> allUsers;

    public AppRepository(Application application) {
        AppDatabase appDb = AppDatabase.getDatabase(application);
        inventoryDAO = appDb.inventoryDAO();
        userDAO = appDb.userDAO();
        allItems = inventoryDAO.getAllInventory();
        allUsers = userDAO.getAllUsers();
    }

    public LiveData<List<Inventory>> getAllData() { return allItems; }
    public LiveData<List<User>> getAllUsersData() {return allUsers;}

    public LiveData<Inventory> get(int id) {
        return inventoryDAO.get(id);
    }
    public LiveData<User> getUser(String userName, String password) {
        return userDAO.getUser(userName, password); }



    //Insert Method
    public void insert(Inventory inventory) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            inventoryDAO.insertInventory(inventory);
        });
    }


    //Update Method
    public void update(Inventory inventory) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            inventoryDAO.updateInventory(inventory);
        });
    }

    //Delete Method
    public void delete(Inventory inventory) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            inventoryDAO.deleteInventory(inventory);
        });
    }

    //User Insert Method
    public void insertUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insertUser(user);
        });
    }

    //User Update Method
    public void updateUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            userDAO.updateUser(user);
        });
    }

    //User Delete Method
    public void deleteUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            userDAO.deleteUser(user);
        });
    }

}
