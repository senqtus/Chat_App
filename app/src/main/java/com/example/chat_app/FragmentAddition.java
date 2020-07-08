package com.example.chat_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private static String headURL = "https://www.kinoafisha.ge/";
    private List<Movie> movieList = new ArrayList<>();;
    private MoviesAdapter mAdapter;
    private RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment_addition, container, false);
         recyclerView = view.findViewById(R.id.movies);
         getPopularMovies();
        mAdapter = new MoviesAdapter(movieList);
        return view;
    }

    private void getPopularMovies()
    {
        GetPopularMovieAsync getPopularMovieAsync = new GetPopularMovieAsync();
        GetPopularMovieAsync.Callback callback = new GetPopularMovieAsync.Callback() {
            @Override
            public void onDataReceived(ArrayList<Movie> movies) {
                movieList = movies;
                mAdapter = new MoviesAdapter(movieList);

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
            ImageView image;
            MyViewHolder(View view) {
                super(view);
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
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            Movie movie = moviesList.get(position);
            holder.image.setImageURI(Uri.parse(movie.getImage()));
            Picasso.get().load(movieList.get(position).getImage()).resize(300,300).centerCrop().into(holder.image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(headURL + movieList.get(position).getURL()));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Not Relevant App found", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }


}
