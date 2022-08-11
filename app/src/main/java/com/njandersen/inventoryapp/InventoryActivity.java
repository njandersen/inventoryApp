package com.njandersen.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.njandersen.inventoryapp.adapter.InventoryRecViewAdapter;
import com.njandersen.inventoryapp.data.Inventory;
import com.njandersen.inventoryapp.model.InventoryViewModel;

import java.util.List;
import java.util.Objects;

public class InventoryActivity extends AppCompatActivity
        implements InventoryRecViewAdapter.OnInventoryClickListener {

    private static final String TAG = "Clicked";
    public static final String INVENTORY_ID = "inventoryId";
    private RecyclerView inventoryRecView;
    private InventoryViewModel inventoryViewModel;
    private InventoryRecViewAdapter invRecViewAdapter;
    private LiveData<List<Inventory>> itemList;


    ActivityResultLauncher<Intent> activitylauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");

                    if(result.getResultCode() == 78) {
                        Intent intent = result.getData();

                        if (intent != null) {
                            String name = intent.getStringExtra(AddInventoryActivity.NAME);
                            String qty = intent.getStringExtra(AddInventoryActivity.QTY);
                            String desc = intent.getStringExtra(AddInventoryActivity.DESCRIPTION);
                            String price = intent.getStringExtra(AddInventoryActivity.PRICE);

                            Inventory inventory = new Inventory(name, qty, desc, price);
                            InventoryViewModel.insert(inventory);
                        }
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_inventory);


        inventoryRecView = findViewById(R.id.inventoryRecView);
        inventoryRecView.setLayoutManager(new GridLayoutManager(this, 2));

        inventoryViewModel = new ViewModelProvider.AndroidViewModelFactory(InventoryActivity.this
                .getApplication())
                .create(InventoryViewModel.class);

        inventoryViewModel.getAllInventory().observe(this, items -> {
            invRecViewAdapter = new InventoryRecViewAdapter(items,
                    InventoryActivity.this, this);
            inventoryRecView.setAdapter(invRecViewAdapter);


        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(InventoryActivity.this, AddInventoryActivity.class);
            activitylauncher.launch(intent);
        });
    }

    @Override
    public void onInventoryClick(int position){
        Inventory inventory = Objects.requireNonNull(inventoryViewModel.allItems.getValue())
                .get(position);
        Log.d(TAG, "onInventoryClick: " + inventory.getId());

        Intent intent = new Intent(InventoryActivity.this, AddInventoryActivity.class);
        intent.putExtra(INVENTORY_ID, inventory.getId());
        startActivity(intent);
    }


}



