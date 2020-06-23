package com.example.chat_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chat_app.file_work.Storage;
import com.example.chat_app.file_work.StorageImplementation;

public class LoginActivity extends AppCompatActivity {


    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        StorageImplementation saver = new StorageImplementation();
        saver.add(this, "User", username.toString());
    }



    public void toActivityMain(View view){
        Intent transfer=new Intent(this,ChatListActivity.class);
        startActivity(transfer);
    }
}
