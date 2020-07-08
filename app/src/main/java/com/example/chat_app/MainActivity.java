package com.example.chat_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chat_app.data.Contact;
import com.example.chat_app.data.ContactList;
import com.example.chat_app.data.Storage;
import com.example.chat_app.data.StorageImplementation;
import com.example.chat_app.services.GetPopularMovieAsync;
import com.example.chat_app.services.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private static String user;
    private static ListView contactListView;
    private static ContactListAdapter contactListAdapter;
    private static ArrayList<Contact> contactList;
    public static MovieArrayAdapter movieArrayAdapter;
    public static ListView popularMovieList;
    private FragmentAddition fragmentAddition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = "";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                user = extras.getString("username");
            }
        }
        setContentView(R.layout.activity_main);
        contactListView = findViewById(R.id.contactViewList);
        context = MainActivity.this;
        contactListAdapter = new ContactListAdapter(context, 0, new ArrayList<Contact>());
        contactList = new ArrayList<Contact>();
        //movieArrayAdapter = new MovieArrayAdapter(context,0, new ArrayList<Movie>());
        //popularMovieList = findViewById(R.id.movies);
        //getPopularMovies();
        getContacts();
        fillData();

        fragmentAddition = (FragmentAddition) getSupportFragmentManager().findFragmentById(R.id.fragmentAddition);


        findViewById(R.id.addContactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });
        findViewById(R.id.logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ChatRoomActivity.class);
                final Contact contact = (Contact) parent.getItemAtPosition(position);
                intent.putExtra("dialog", contact.username);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });
    }

    private void getPopularMovies()
    {
        GetPopularMovieAsync getPopularMovieAsync = new GetPopularMovieAsync();
        GetPopularMovieAsync.Callback callback = new GetPopularMovieAsync.Callback() {
            @Override
            public void onDataReceived(ArrayList<Movie> movies) {
                popularMovieList.setAdapter(movieArrayAdapter);
                movieArrayAdapter.addAll(movies);
            }
        };
        getPopularMovieAsync.setCallback(callback);
        getPopularMovieAsync.execute();
    }


    private static void getContacts()
    {
        Storage storage = new StorageImplementation(user);
        Object storageAsObject = storage
                .getObject(context, ContactList.STORAGE_KEY, ContactList.class);

        ContactList contactLst;
        if (storageAsObject != null) {
            contactLst = (ContactList) storageAsObject;
        } else {
            contactLst = new ContactList();
        }
        contactList = contactLst.getContacts();
    }

    private static void fillData()
    {
        contactListAdapter.clear();
        contactListView.setAdapter(contactListAdapter);
        contactListAdapter.addAll(contactList);
    }


    public class ContactListAdapter extends ArrayAdapter<Contact>
    {
        private Context context;

        public ContactListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_individiual_chat_on_main, parent, false);
            final Contact contact = getItem(position);

            TextView username = view.findViewById(R.id.username);
            TextView userLetter = view.findViewById(R.id.userLetter);
            username.setText(contact.username);
            userLetter.setText(contact.userLetter);
            return view;
        }
    }


    class MovieArrayAdapter extends ArrayAdapter<Movie> {

        private Context context;

        public MovieArrayAdapter(Context context, int resource, List<Movie> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_popular_movie, parent, false);
            Movie currentMovie = getItem(position);
            TextView textView = view.findViewById(R.id.movieName);
            textView.setText(currentMovie.getName());
            ImageView imageView = view.findViewById(R.id.movieImage);
            Picasso.get().load(currentMovie.getImage()).into(imageView);
            return view;
        }
    }

}
