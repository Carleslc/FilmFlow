package com.and119_idi.myfilmdatabase.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.controller.MoviesRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.OnItemClickListener;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

import java.util.List;

/**
 * Created by Carlos Lázaro Costa on 11/12/16.
 */
public class MainMoviesFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private MoviesRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_movies, container, false);

        mRecyclerView = (RecyclerView) ret.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.swipeRefreshLayout);

        SwipeRefreshLayout.OnRefreshListener resfreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFilms();
            }
        };
        mSwipeRefreshLayout.setOnRefreshListener(resfreshListener);
        resfreshListener.onRefresh();

        return ret;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        FloatingActionButton activity_fab = (FloatingActionButton) getActivity().findViewById(R.id.add_films_button);
        activity_fab.show();
        super.onActivityCreated(savedInstanceState);
    }

    protected void refreshFilms() {
        new FetchFilmsTask().execute();
    }

    public class FetchFilmsTask extends AsyncTask<Void, Void, List<Film> > {

        private FilmData filmData;
        private List<Film> moviesList;

        protected MoviesRecyclerViewAdapter getMoviesRecyclerViewAdapter(List<Film> moviesList) {
            return new MoviesRecyclerViewAdapter(moviesList);
        }

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
                adapter = getMoviesRecyclerViewAdapter(moviesList);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Film film) {
                        Toast.makeText(getContext(), film.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
                mRecyclerView.setAdapter(adapter);
            }
            else {
                adapter.getFilmList().clear();
                adapter.getFilmList().addAll(moviesList);
                adapter.notifyDataSetChanged();
            }

            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
