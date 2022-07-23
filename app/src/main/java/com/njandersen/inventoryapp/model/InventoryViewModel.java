package com.njandersen.inventoryapp.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.njandersen.inventoryapp.data.AppRepository;
import com.njandersen.inventoryapp.data.Inventory;

import java.util.List;

public class InventoryViewModel extends AndroidViewModel {
    public static AppRepository repository;
    public final LiveData<List<Inventory>> allItems;

    //Constructor
    public InventoryViewModel(Application application) {
        super(application);
        repository = new AppRepository(application);
        allItems = repository.getAllData();
    }

    public LiveData<List<Inventory>> getAllInventory() {return allItems; }

    public static void insert(Inventory inventory) {repository.insert(inventory);}

    public void update(Inventory inventory) {repository.update(inventory);}

    public void delete(Inventory inventory) {repository.delete(inventory);}
}
