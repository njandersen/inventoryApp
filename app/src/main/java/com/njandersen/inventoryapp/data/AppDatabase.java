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
                        UserDAO userDAO = Instance.userDAO();
                        inventoryDAO.deleteAll();


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
    public abstract UserDAO userDAO();


}
