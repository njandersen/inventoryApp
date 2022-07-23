package com.njandersen.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.njandersen.inventoryapp.data.Inventory;
import com.njandersen.inventoryapp.model.InventoryViewModel;


public class AddInventoryActivity extends AppCompatActivity {

    //Variables for edit text fields and button
    private EditText enterName;
    private EditText enterQty;
    private EditText enterDescription;
    private EditText enterPrice;
    private Button saveButton;
    //Sets a new view model
    private InventoryViewModel inventoryViewModel;


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

        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            //Makes sure all the text fields are not empty.
            if (!TextUtils.isEmpty(enterName.getText())
                    && !TextUtils.isEmpty(enterQty.getText())
                    && !TextUtils.isEmpty(enterDescription.getText())
                    && !TextUtils.isEmpty(enterPrice.getText())) {

                Inventory inventory = new Inventory(enterName.getText().toString(),
                        enterQty.getText().toString(), enterDescription.getText().toString(),
                        enterPrice.getText().toString());

                InventoryViewModel.insert(inventory);

            } else {
                Toast.makeText(this, "Please Enter All Fields", Toast.LENGTH_SHORT)
                        .show();
            }
            finish();

        });

    }


}
