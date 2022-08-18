package com.njandersen.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.njandersen.inventoryapp.data.Inventory;
import com.njandersen.inventoryapp.model.InventoryViewModel;


public class AddInventoryActivity extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String QTY = "qty";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    //Variables for edit text fields and button
    private EditText enterName;
    private EditText enterQty;
    private EditText enterDescription;
    private EditText enterPrice;
    private Button saveButton;
    //Sets a new view model
    private InventoryViewModel inventoryViewModel;

    private int inventoryId = 0;
    private Boolean isEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        enterName = findViewById(R.id.addItemName);
        enterQty = findViewById(R.id.addItemQty);
        enterDescription = findViewById(R.id.addItemDescription);
        enterPrice = findViewById(R.id.addItemPrice);
        saveButton = findViewById(R.id.saveButton);

        inventoryViewModel = new ViewModelProvider.AndroidViewModelFactory(AddInventoryActivity.this
                .getApplication())
                .create(InventoryViewModel.class);

        //Sets the add inventory page to display info of card clicked.
        Bundle data = getIntent().getExtras();
        if (getIntent().hasExtra(InventoryActivity.INVENTORY_ID)) {
            inventoryId = getIntent().getIntExtra(InventoryActivity.INVENTORY_ID, 0);
            inventoryViewModel.get(inventoryId).observe(this, inventory -> {
                if (inventory != null) {
                    enterName.setText(inventory.getItemName());
                    enterQty.setText(inventory.getItemQty());
                    enterDescription.setText(inventory.getItemDescription());
                    enterPrice.setText(inventory.getItemPrice());
                }
            });
            isEdit = true;
        }

        //Saves the information entered in the edit text fields to the database.
        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            //Makes sure all the text fields are not empty.
            if (!TextUtils.isEmpty(enterName.getText())
                    && !TextUtils.isEmpty(enterQty.getText())
                    && !TextUtils.isEmpty(enterDescription.getText())
                    && !TextUtils.isEmpty(enterPrice.getText())) {

                String name = enterName.getText().toString();
                String qty = enterQty.getText().toString();
                String description = enterDescription.getText().toString();
                String price = enterPrice.getText().toString();

                replyIntent.putExtra(NAME, name);
                replyIntent.putExtra(QTY, qty);
                replyIntent.putExtra(DESCRIPTION, description);
                replyIntent.putExtra(PRICE, price);
                setResult(78, replyIntent);

            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            AddInventoryActivity.super.onBackPressed();

        });

        //Update button
        Button updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(view -> {
            String name = enterName.getText().toString().trim();
            String qty = enterQty.getText().toString().trim();
            String desc = enterDescription.getText().toString().trim();
            String price = enterPrice.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(qty) || TextUtils.isEmpty(desc) ||
            TextUtils.isEmpty(price)) {
                Snackbar.make(enterName, "fields are empty", Snackbar.LENGTH_SHORT).show();
            } else {
               Inventory inventory = new Inventory();
               inventory.setId(inventoryId);
               inventory.setItemName(name);
               inventory.setItemQty(qty);
               inventory.setItemDescription(desc);
               inventory.setItemPrice(price);
               InventoryViewModel.update(inventory);
               finish();
            }
        });

        //Delete button
        Button deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(view -> {
            String name = enterName.getText().toString().trim();
            String qty = enterQty.getText().toString().trim();
            String desc = enterDescription.getText().toString().trim();
            String price = enterPrice.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(qty) || TextUtils.isEmpty(desc) ||
                    TextUtils.isEmpty(price)) {
                Snackbar.make(enterName, "fields are empty", Snackbar.LENGTH_SHORT).show();
            } else {
                Inventory inventory = new Inventory();
                inventory.setId(inventoryId);
                inventory.setItemName(name);
                inventory.setItemQty(qty);
                inventory.setItemDescription(desc);
                inventory.setItemPrice(price);
                InventoryViewModel.delete(inventory);
                finish();
            }
        });

        //Sets the save button to appear when adding new item, makes it invisible when updating.
        if (isEdit) {
            saveButton.setVisibility(View.GONE);
        }else {
            updateBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }

    }


}
