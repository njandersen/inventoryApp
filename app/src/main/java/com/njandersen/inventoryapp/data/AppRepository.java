package com.njandersen.inventoryapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private InventoryDAO inventoryDAO;
    private UserDAO userDAO;
    private LiveData<List<Inventory>> allItems;

    public AppRepository(Application application) {
        AppDatabase appDb = AppDatabase.getDatabase(application);
        inventoryDAO = appDb.inventoryDAO();
        userDAO = appDb.userDAO();
        allItems = inventoryDAO.getAllInventory();
    }

    public LiveData<List<Inventory>> getAllData() { return allItems; }

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

}
