package com.njandersen.inventoryapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InventoryDAO {

    @Query("SELECT * FROM Inventory ORDER BY item_name ASC")
    LiveData<List<Inventory>> getAllInventory();

    @Query("SELECT * FROM Inventory WHERE Inventory.id == :id")
    LiveData<Inventory> get(int id);


    //crud operations
    @Insert
    void insertInventory(Inventory item);

    @Update
    void updateInventory(Inventory item);

    @Delete
    void deleteInventory(Inventory item);

    @Query("DELETE FROM Inventory")
    void deleteAll();
}
