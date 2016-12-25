package com.and119_idi.myfilmdatabase.view;

import com.and119_idi.myfilmdatabase.controller.MoviesDetailedRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.MoviesRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.model.Film;

import java.util.List;

/**
 * Created by albert on 29/11/16.
 */
public class DetailedMoviesFragment extends MainMoviesFragment {

    protected void refreshFilms() {
        new FetchFilmsTask() {
            protected MoviesRecyclerViewAdapter getMoviesRecyclerViewAdapter(List<Film> moviesList) {
                return new MoviesDetailedRecyclerViewAdapter(moviesList);
            }
        }.execute();
    }

}
