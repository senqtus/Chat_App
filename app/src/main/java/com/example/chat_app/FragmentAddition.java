package com.example.chat_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat_app.services.GetPopularMovieAsync;
import com.example.chat_app.services.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FragmentAddition extends Fragment {
    private List<Movie> movieList = new ArrayList<>();;
    private MoviesAdapter mAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment_addition, container, false);
         recyclerView = view.findViewById(R.id.movies);
         getPopularMovies();
        /*mAdapter = new MoviesAdapter(movieList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
        return view;
    }

    private void getPopularMovies()
    {
        GetPopularMovieAsync getPopularMovieAsync = new GetPopularMovieAsync();
        GetPopularMovieAsync.Callback callback = new GetPopularMovieAsync.Callback() {
            @Override
            public void onDataReceived(ArrayList<Movie> movies) {
                mAdapter = new MoviesAdapter(movieList);
                movieList = movies;
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }
        };
        getPopularMovieAsync.setCallback(callback);
        getPopularMovieAsync.execute();
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<Movie> moviesList;
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView image;
            MyViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.movieName);
                image = view.findViewById(R.id.movieImage);
            }
        }
        public MoviesAdapter(List<Movie> moviesList) {
            this.moviesList = moviesList;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_popular_movie, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Movie movie = moviesList.get(position);
            holder.title.setText(movie.getName());
            holder.image.setImageURI(Uri.parse(movie.getImage()));
            Picasso.get().load(movieList.get(position).getImage()).into(holder.image);
        }
        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }

}
