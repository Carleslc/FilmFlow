package com.and119_idi.myfilmdatabase.view;

import com.and119_idi.myfilmdatabase.controller.MoviesDetailedRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.MoviesRecyclerViewAdapter;

/**
 * Created by albert on 29/11/16.
 */
public class DetailedMoviesFragment extends MainMoviesFragment {

    protected void refreshFilms() {
        new FetchFilmsTask() {
            protected MoviesRecyclerViewAdapter getMoviesRecyclerViewAdapter() {
                return new MoviesDetailedRecyclerViewAdapter();
            }
        }.execute();
    }

}
