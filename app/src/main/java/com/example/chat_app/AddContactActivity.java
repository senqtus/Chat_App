package com.example.chat_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_app.data.Chat;
import com.example.chat_app.data.Contact;
import com.example.chat_app.data.ContactList;
import com.example.chat_app.data.Storage;
import com.example.chat_app.data.StorageImplementation;
import com.example.chat_app.data.TextMessage;

public class AddContactActivity extends AppCompatActivity {
    private static Context context;
    private static String user;
    private static String contactUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        user = "";
        context = getApplicationContext();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                user = extras.getString("username");
            }
        }
        findViewById(R.id.addContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText contact = findViewById(R.id.dialogPerson);
                contactUser = contact.getText().toString();
                addContact(user, contactUser);
                addContact(contactUser, user);
                Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
                finish();
            }
        });
    }



    private static void addContact(String username, String toContact)
    {
        Storage storage = new StorageImplementation(username);
        Object storageAsObject = storage
                .getObject(context, ContactList.STORAGE_KEY, ContactList.class);

        ContactList contactList;
        if (storageAsObject != null) {
            contactList = (ContactList) storageAsObject;
        } else {
            contactList = new ContactList();
        }

        Contact contact = new Contact();
        contact.username = toContact;
        contact.userLetter = toContact.substring(0, 1);
        contact.lastMessage = "";
        contactList.getContacts().add(contact);
        storage.add(context, ContactList.STORAGE_KEY, contactList);
    }

}
