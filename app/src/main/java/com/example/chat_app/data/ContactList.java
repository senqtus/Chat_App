package com.example.chat_app.data;

import android.content.Context;

import java.util.ArrayList;

public class ContactList {
    public static String STORAGE_KEY = "friends";

    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setMessages(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

}
