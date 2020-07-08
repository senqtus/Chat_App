package com.example.chat_app.services;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetPopularMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private Callback callback;

    @Override
    protected void onPreExecute() {
        //initial jobs here
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.kinoafisha.ge/").get();
            Elements elements = doc.getElementsByClass("movie");
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                Elements h5Tags = element.getElementsByTag("h5");
                Movie movie = new Movie();
                if (h5Tags.size() > 0) {
                    String movieName = h5Tags.get(0).text();
                    movie.setName(movieName);
                }
                Elements aTags = element.getElementsByTag("img");
                if (aTags.size() > 0) {
                    String imageUrl = aTags.get(0).attributes().get("src");
                    movie.setImage(imageUrl);
                }
                arrayList.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        //main thread here
        if (callback != null) {
            callback.onDataReceived(movies);
        }
    }

    public interface Callback {

        void onDataReceived(ArrayList<Movie> movies);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
