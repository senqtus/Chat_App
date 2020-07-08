package com.example.chat_app.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class StorageImplementation implements Storage {

    public String fileName;

    public StorageImplementation(String fileName){
        this.fileName = fileName;
    }

    public void add(Context context, String key, Object value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, new Gson().toJson(value));
        editor.commit();
    }

    @Override
    public Object getObject(Context context, String key, Class clas) {
        SharedPreferences sharedPreferences = getInstance(context);
        String data = sharedPreferences.getString(key, null);
        return data == null ? null : new Gson().fromJson(data, clas);
    }


    private SharedPreferences getInstance(Context context) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
}
