package com.njandersen.inventoryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LongDef;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.core.Context;
import com.njandersen.inventoryapp.adapter.InventoryRecViewAdapter;
import com.njandersen.inventoryapp.data.Inventory;
import com.njandersen.inventoryapp.model.InventoryViewModel;

import java.util.List;
import java.util.Objects;

public class InventoryActivity extends AppCompatActivity
        implements InventoryRecViewAdapter.OnInventoryClickListener {

    private static final String TAG = "Clicked";
    public static final String INVENTORY_ID = "inventoryId";
    private static final int USER_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static boolean smsAuthorized = false;
    static String PhoneNumberHolder;
    private RecyclerView inventoryRecView;
    private InventoryViewModel inventoryViewModel;
    private InventoryRecViewAdapter invRecViewAdapter;
    private LiveData<List<Inventory>> itemList;
    androidx.appcompat.app.AlertDialog AlertDialog = null;
    private Button buttonSMS;


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

        buttonSMS = findViewById(R.id.buttonSMS);


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

        // Adding click listener to buttonSMS
        buttonSMS.setOnClickListener(view -> {

            // Request SMS permissions for the device
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this, "SMS permission is required", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS},
                            USER_PERMISSIONS_REQUEST_SEND_SMS);
                }
            } else {
                Toast.makeText(this, "Device SMS permission is allowed", Toast.LENGTH_LONG).show();
            }
            // Open SMS Alert Dialog
            AlertDialog = AlertSMS.doubleButton(this);
            AlertDialog.show();
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

    // Receive and evaluate user response from AlertDialog to send SMS
    public static void AllowSendSMS() {
        smsAuthorized = true;
    }

    public static void DenySendSMS() {
        smsAuthorized = false;
    }

    public void SendSMSMessage(Context context) {
        String phoneNo = PhoneNumberHolder;
        String smsMsg = "Warning, Inventory low";

        // Evaluate AlertDialog permission to send SMS
        if (smsAuthorized) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, smsMsg, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Device permission denied", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "SMS alert disabled", Toast.LENGTH_LONG).show();
        }
    }


}



