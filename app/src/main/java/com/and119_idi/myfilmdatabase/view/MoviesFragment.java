package com.and119_idi.myfilmdatabase.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.controller.MoviesRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.MoviesSimpleRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.OnItemClickListener;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

import java.util.List;

/**
 * Created by albert on 29/11/16.
 */
public class MoviesFragment extends MainMoviesFragment {

    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_movies, container, false);

        mRecyclerView = (RecyclerView) ret.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFilms();
            }
        });

        getFilms();

        return ret;
    }

    private void getFilms() {
        new MoviesFragment.FetchFilmsTask().execute();
    }


    public class FetchFilmsTask extends AsyncTask<Void, Void, List<Film> > {

        private FilmData filmData;
        private List<Film> moviesList;



        @Override
        protected List<Film> doInBackground(Void... params) {
            try {

                filmData = new FilmData(getContext());
                filmData.open();
                moviesList = filmData.getAllFilms();
                filmData.close();
                return moviesList;

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Film> moviesList) {

            if (adapter == null) {
                adapter = new MoviesRecyclerViewAdapter(moviesList);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Film film) {
                        Toast.makeText(getContext(), film.getTitle(), Toast.LENGTH_LONG).show();
                    }
                });
                mRecyclerView.setAdapter(adapter);
            }
            else {
                adapter.getList().clear();
                adapter.getList().addAll(moviesList);
                adapter.notifyDataSetChanged();
            }

            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
