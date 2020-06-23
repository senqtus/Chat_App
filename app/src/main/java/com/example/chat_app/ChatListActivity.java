package com.example.chat_app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_app.file_work.Storage;
import com.example.chat_app.file_work.StorageImplementation;

import java.util.ArrayList;
import java.util.List;


public class ChatListActivity extends Activity {
    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView chats = findViewById(R.id.chatList);
        ArrayList<ChatView> chatViews;
    }

    private void getData()
    {
        Storage storage = new StorageImplementation();
        Object storageAsObject = storage.getObject(this, ChatView.STORAGE_KEY, ChatView.class);

        ChatView chatStorage;
        if (storageAsObject != null) {
            chatStorage = (ChatView) storageAsObject;
        } else {
            chatStorage = new ChatView();
        }
        this.chatView = chatStorage.getMessages();
    }

    private void fillData()
    {
        aliceChatArrayAdapter.clear();
        aliceListView.setAdapter(aliceChatArrayAdapter);
        aliceChatArrayAdapter.addAll(chat);
    }
}

class AdapterChats extends ArrayAdapter<ChatView>{
    private Context context;
    public AdapterChats(@NonNull Context context, int resource, @NonNull ArrayList<ChatView> objects) {
        super(context, resource, objects);
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_individiual_chat_on_main, parent, false);
        final ChatView chat = getItem(position);

        TextView message = view.findViewById(R.id.aliceMessage);
        message.setText(textMessage.getMessage());
        if (textMessage.isSender()) {
            message.setTextColor(getResources().getColor(R.color.aliceText));
            message.setGravity(Gravity.LEFT);
        }
        else {
            message.setTextColor(getResources().getColor(R.color.bobText));
            message.setGravity(Gravity.RIGHT);
        }
        return view;
    }
}
