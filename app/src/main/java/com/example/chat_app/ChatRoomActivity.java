package com.example.chat_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_app.data.Chat;
import com.example.chat_app.data.Contact;
import com.example.chat_app.data.Storage;
import com.example.chat_app.data.StorageImplementation;
import com.example.chat_app.data.TextMessage;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private static String dialog;
    private static String user;
    private static Context context;
    private static ListView texts;
    private static ArrayList<TextMessage> messages;
    private static MessagesListAdapter messagesListAdapter;
    private EditText message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        message = findViewById(R.id.message);
        context = ChatRoomActivity.this;
        texts = findViewById(R.id.messageList);
        messagesListAdapter = new MessagesListAdapter(context, 0, new ArrayList<TextMessage>());
        messages = new ArrayList<TextMessage>();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                dialog = extras.getString("dialog");
                user = extras.getString("username");
            }
        }
        TextView contact = findViewById(R.id.ContactName);
        contact.setText(dialog);
        getChat();
        fillData();
        findViewById(R.id.backToMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatRoomActivity.this, MainActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.addMessageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMessage(user, dialog,message.getText().toString(), true);
                addMessage(dialog, user, message.getText().toString(),false);
                message.setText("");
                getChat();
                fillData();
            }
        });
    }

    private static void addMessage(String one, String other,String sms, boolean sender)
    {
        Storage storage = new StorageImplementation(one);
        Object storageAsObject = storage
                .getObject(context, other, Chat.class);

        Chat chatStorage;
        if (storageAsObject != null) {
            chatStorage = (Chat) storageAsObject;
        } else {
            chatStorage = new Chat(other);
        }
        TextMessage message = new TextMessage();
        message.setMessage(sms);
        message.setSender(sender);
        chatStorage.getMessages().add(message);
        storage.add(context, other, chatStorage);
    }



    private static void getChat()
    {
        Storage storage = new StorageImplementation(user);
        Object storageAsObject = storage
                .getObject(context, dialog, Chat.class);

        Chat chat;
        if (storageAsObject != null) {
            chat = (Chat) storageAsObject;
        } else {
            chat = new Chat(dialog);
        }
        messages = chat.getMessages();
    }

    private static void fillData()
    {
        messagesListAdapter.clear();
        texts.setAdapter(messagesListAdapter);
        messagesListAdapter.addAll(messages);
    }

    public class MessagesListAdapter extends ArrayAdapter<TextMessage>
    {
        private Context context;

        public MessagesListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TextMessage> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_individual_message, parent, false);
            final TextMessage textMessage = getItem(position);

            TextView message = view.findViewById(R.id.messageText);
            message.setText(textMessage.getMessage());

            if (textMessage.isSender()) {
                message.setBackgroundResource(R.drawable.send_message_border);
                message.setGravity(Gravity.RIGHT);
            }
            else {
                message.setBackgroundResource(R.drawable.recieved_message_border);
                message.setGravity(Gravity.LEFT);
            }
            return view;
        }
    }
}
