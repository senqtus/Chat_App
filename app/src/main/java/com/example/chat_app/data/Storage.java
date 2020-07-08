package com.example.chat_app.data;

import android.content.Context;

public interface Storage {
    void add(Context context, String key, Object value);

    Object getObject(Context context, String key, Class clas);
}
