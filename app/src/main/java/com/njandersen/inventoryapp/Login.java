package com.njandersen.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;


import com.njandersen.inventoryapp.data.User;
import com.njandersen.inventoryapp.model.UserViewModel;

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private Button forgotPassword;
    private Button newUser;
    private int userId = 0;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider.AndroidViewModelFactory(Login.this
                .getApplication())
                .create(UserViewModel.class);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        newUser = findViewById(R.id.newUserBtn);


        login.setOnClickListener(view -> {
            String userNameText = username.getText().toString();
            String userPassword = password.getText().toString();
            LiveData<User> user = userViewModel.getUser(userNameText, userPassword);
            if (user != null) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent newIntent = new Intent(Login.this, InventoryActivity.class);
                Login.this.startActivity(newIntent);
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Error wrong credentials", Toast.LENGTH_SHORT).show();
            }
        });

        newUser.setOnClickListener(view -> {
            Intent newIntent = new Intent(Login.this, RegisterUser.class);
            Login.this.startActivity(newIntent);
        });
    }


}
