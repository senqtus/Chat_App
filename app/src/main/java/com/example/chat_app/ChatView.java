package com.example.chat_app;


public class ChatView {
    public static String STORAGE_KEY="ChatViews";

    public String name,text;
    public ChatView(String name,String text){
        this.name=name;
        this.text=text;
    }
    public ChatView(){}

}
