package com.njandersen.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.njandersen.inventoryapp.data.AppDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView currentInventoryCard;
    private CardView pastOrdersCard;
    private CardView addInventoryCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Click listener for cards.
        currentInventoryCard = (CardView) findViewById(R.id.currentinventorycard);
        pastOrdersCard = (CardView) findViewById(R.id.pastorderscard);
        addInventoryCard = (CardView) findViewById(R.id.addInventoryCard);

        currentInventoryCard.setOnClickListener(this);
        pastOrdersCard.setOnClickListener(this);
        addInventoryCard.setOnClickListener(this);

    }

    //Switch statement to switch activities.
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.currentinventorycard:
                i = new Intent(this, InventoryActivity.class);
                startActivity(i);
                break;
            case R.id.pastorderscard:
                Toast.makeText(MainActivity.this, "Past Orders Clicked",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.addInventoryCard:
                i = new Intent(this, AddInventoryActivity.class);
                startActivity(i);
            break;
        }
    }

}