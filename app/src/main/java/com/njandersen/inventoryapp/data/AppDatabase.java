package com.njandersen.inventoryapp.data;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.nio.file.attribute.UserPrincipal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Inventory.class, User.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    //Pushes database into background thread
    private static volatile AppDatabase Instance;
    private static final int NUM_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUM_OF_THREADS);

    private static final  RoomDatabase.Callback roomDbCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {
                        InventoryDAO inventoryDAO = Instance.inventoryDAO();
                        inventoryDAO.deleteAll();

                        Inventory inventory = new Inventory("Ryzen 7 5700x", "32",
                                "AMD 3rd Gen CPU", "$274.99");
                        inventoryDAO.insertInventory(inventory);

                        inventory = new Inventory( "Ryzen 9 5900x", "15",
                                "AMD 3rd Gen CPU", "$349.99");
                        inventoryDAO.insertInventory(inventory);

                        inventory = new Inventory("Gigabyte 3080", "24",
                                "Nvidia 30 series graphics card", "$920.99" );
                        inventoryDAO.insertInventory(inventory);
                    });
                }

            };

    //Singleton pattern for database
    static AppDatabase getDatabase(final Context context) {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(roomDbCallback)
                            .build();
                }
            }
        }
        return Instance;
    }

    public abstract InventoryDAO inventoryDAO();


}
