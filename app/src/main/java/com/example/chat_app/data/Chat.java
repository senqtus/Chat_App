package com.example.chat_app.data;

import android.content.Context;

import java.util.ArrayList;

public class Chat {

    public String storageKey;

    public Chat(String storageKey)
    {
        this.storageKey = storageKey;
    }

    private ArrayList<TextMessage> messages = new ArrayList<TextMessage>();

    public ArrayList<TextMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<TextMessage> messages) {
        this.messages = messages;
    }

    public static void addMessage(Context context, String textMessage, boolean sender)
    {
        TextMessage message = new TextMessage();
        message.setMessage(textMessage);
        message.setSender(sender);
    }
}
