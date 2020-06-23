package com.example.chat_app.file_work;

import android.content.Context;

public interface Storage {

    void add(Context context, String key, Object obj);

    Object getObject(Context context, String key, Class className);
}
