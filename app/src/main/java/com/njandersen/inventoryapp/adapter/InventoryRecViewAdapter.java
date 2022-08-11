package com.njandersen.inventoryapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.njandersen.inventoryapp.R;
import com.njandersen.inventoryapp.data.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryRecViewAdapter extends RecyclerView.Adapter<InventoryRecViewAdapter.ViewHolder> {
    private OnInventoryClickListener inventoryClickListener;
    private final List<Inventory> itemList;
    private final Context context;

    public InventoryRecViewAdapter(List<Inventory> itemList, Context context, OnInventoryClickListener inventoryClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.inventoryClickListener = inventoryClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item,
                parent, false);

        return new ViewHolder(view, inventoryClickListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Inventory inventory = Objects.requireNonNull(itemList.get(position));
        holder.itemName.setText(inventory.getItemName());
        holder.itemQty.setText(inventory.getItemQty());
    }


    @Override
    public int getItemCount(){
        return Objects.requireNonNull(itemList.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnInventoryClickListener onInventoryClickListener;
        public TextView itemName, itemQty;
        public CardView parent;

        //Constructor
        public ViewHolder(View itemView, OnInventoryClickListener onInventoryClickListener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item);
            itemQty = itemView.findViewById(R.id.qty);
            parent = itemView.findViewById(R.id.parent);
            this.onInventoryClickListener = onInventoryClickListener;

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onInventoryClickListener.onInventoryClick(getAdapterPosition());
        }
    }
    //Click listener for the the curr inventory screen
    public interface OnInventoryClickListener {
        void onInventoryClick(int position);
    }
}


