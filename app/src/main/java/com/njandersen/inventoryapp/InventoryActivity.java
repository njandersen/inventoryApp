package com.njandersen.inventoryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.njandersen.inventoryapp.adapter.InventoryRecViewAdapter;
import com.njandersen.inventoryapp.data.Inventory;
import com.njandersen.inventoryapp.model.InventoryViewModel;

import java.util.List;

public class InventoryActivity extends AppCompatActivity {
    private RecyclerView inventoryRecView;
    private InventoryViewModel inventoryViewModel;
    private InventoryRecViewAdapter invRecViewAdapter;
    private LiveData<List<Inventory>> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_inventory);
        configBackButton();

        inventoryRecView = findViewById(R.id.inventoryRecView);
        inventoryRecView.setLayoutManager(new GridLayoutManager(this, 2));

        inventoryViewModel = new ViewModelProvider.AndroidViewModelFactory(InventoryActivity.this
                .getApplication())
                .create(InventoryViewModel.class);

        inventoryViewModel.getAllInventory().observe(this, items -> {
            invRecViewAdapter = new InventoryRecViewAdapter(items,
                    InventoryActivity.this);
            inventoryRecView.setAdapter(invRecViewAdapter);


        });
    }

    private void configBackButton() {
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}



