package com.njandersen.inventoryapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.njandersen.inventoryapp.data.User;
import com.njandersen.inventoryapp.model.UserViewModel;

public class RegisterUser extends AppCompatActivity {

    //Edit text and button variables
    private EditText enterFirstName;
    private EditText enterLastName;
    private EditText enterPassword;
    private EditText enterUsername;
    private Button registerBtn;

    private UserViewModel userViewModel;
    private int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        enterFirstName = findViewById(R.id.firstName);
        enterLastName = findViewById(R.id.lastName);
        enterPassword = findViewById(R.id.password);
        enterUsername = findViewById(R.id.username);
        registerBtn = findViewById(R.id.registerBtn);

        userViewModel = new ViewModelProvider.AndroidViewModelFactory(RegisterUser.this
                .getApplication())
                .create(UserViewModel.class);

        registerBtn.setOnClickListener(view -> {
            String firstName = enterFirstName.getText().toString().trim();
            String lastName = enterLastName.getText().toString().trim();
            String password = enterPassword.getText().toString().trim();
            String username = enterUsername.getText().toString().trim();

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                Toast.makeText(RegisterUser.this, "Please fill out all fields.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                User user = new User();
                user.setId(userId);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPassword(password);
                user.setUserName(username);
                UserViewModel.insertUser(user);
                finish();
            }
        });

    }


}
