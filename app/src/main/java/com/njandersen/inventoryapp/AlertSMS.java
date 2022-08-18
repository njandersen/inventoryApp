package com.njandersen.inventoryapp;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class AlertSMS {
    public static AlertDialog doubleButton(final InventoryActivity context) {

        // Builder class for dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.alert_sms_title)
                .setCancelable(false)
                .setMessage(R.string.alert_sms_msg)
                .setPositiveButton(R.string.alert_sms_enable_button, (dialog, arg1) -> {
                    Toast.makeText(context, "SMS alerts have been enabled", Toast.LENGTH_LONG).show();
                    InventoryActivity.AllowSendSMS();
                    dialog.cancel();
                })
                .setNegativeButton(R.string.alert_sms_disable_button, (dialog, arg1) -> {
                    Toast.makeText(context, "SMS alerts have been disabled", Toast.LENGTH_LONG).show();
                    InventoryActivity.DenySendSMS();
                    dialog.cancel();
                });

        // AlertDialog object return
        return builder.create();
    }
}
