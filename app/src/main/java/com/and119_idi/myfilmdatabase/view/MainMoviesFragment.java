package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.controller.MoviesRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

import java.util.List;

/**
 * Created by Carlos Lázaro Costa on 11/12/16.
 */
public class MainMoviesFragment extends Fragment {

    private static final String TAG = MainMoviesFragment.class.getSimpleName();

    private static final int DETAILS_ACTIVITY_RESULT_CODE = 1;
    private static final int ADD_FILM_RESULT_CODE = 2;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MoviesRecyclerViewAdapter adapter;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_movies, container, false);

        mRecyclerView = (RecyclerView) ret.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.swipeRefreshLayout);
        mFloatingActionButton = (FloatingActionButton) ret.findViewById(R.id.add_films_button);
        initListeners();
        new FetchFilmsTask().execute();

        return ret;
    }

    private void initListeners() {
        mFloatingActionButton.setOnClickListener((v) ->
                startActivityForResult(new Intent(getContext(), AddFilmActivity.class), ADD_FILM_RESULT_CODE)
        );
        mSwipeRefreshLayout.setOnRefreshListener(() -> new FetchFilmsTask().execute());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    protected MoviesRecyclerViewAdapter getMoviesRecyclerViewAdapter() {
        return new MoviesRecyclerViewAdapter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        new FetchFilmsTask().execute();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class FetchFilmsTask extends AsyncTask<Void, Void, Boolean> {

        private FilmData filmData;
        private List<Film> moviesList;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                filmData = new FilmData(getContext());
                filmData.open();
                moviesList = filmData.getAllFilms();
                filmData.close();
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Failed to fetch data", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            } else {
                if (adapter == null) {
                    adapter = getMoviesRecyclerViewAdapter();
                    adapter.setOnItemClickListener((film) -> {
                        Log.d(TAG, "Received film: " + film.getTitle()
                                + " with description: " + film.getDescription());
                            startActivityForResult(
                                    new Intent(getContext(), DetailsActivity.class)
                                            .putExtra("film", film),
                                    DETAILS_ACTIVITY_RESULT_CODE
                            );
                        }
                    );
                    mRecyclerView.setAdapter(adapter);
                }
                adapter.setFilms(moviesList);
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
