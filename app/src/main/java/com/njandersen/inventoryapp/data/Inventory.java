package com.njandersen.inventoryapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Inventory {

    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "qty")
    private String itemQty;

    @ColumnInfo(name = "description")
    private String itemDescription;

    @ColumnInfo(name = "price")
    private String itemPrice;

    //Default Constructor
    public Inventory(String itemName, String itemQty,
                     String itemDescription, String itemPrice) {

        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    //Secondary Constructor
    @Ignore
    public Inventory(String itemName, String itemQty) {
        this.itemName = itemName;
        this.itemQty = itemQty;
    }

    public Inventory() {

    }

    public int getId() {
        return id;
    }

    public void setId(int sku) {
        this.id = sku;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
