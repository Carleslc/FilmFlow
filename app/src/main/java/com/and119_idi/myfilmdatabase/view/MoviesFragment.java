package com.and119_idi.myfilmdatabase.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.and119_idi.myfilmdatabase.model.FilmData;
import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.controller.OnItemClickListener;
import com.and119_idi.myfilmdatabase.model.Film;

import java.util.List;

/**
 * Created by albert on 29/11/16.
 */

public class MoviesFragment extends Fragment {

    private List<Film> moviesList;
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter adapter;
    private ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.moviesfraglayout,container,false);

        mRecyclerView = (RecyclerView) ret.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) ret.findViewById(R.id.progress_bar);

        new dbTask().execute();

        return ret;
    }

    //Creamos una AsyncTask para usar la base de datos
    public class dbTask extends AsyncTask<String, Void, Integer> {

        private FilmData filmData;
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            filmData = new FilmData(getContext());
            filmData.open();

        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                moviesList = filmData.getAllFilms();
            }
            catch (Exception e) {
                return 0;
            }
            if (moviesList == null) return 0;
            else return 1;

        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new MoviesRecyclerViewAdapter(getContext(), moviesList);

                //Aqui usamos AS fuertemente y le pasamos la implementación que queremos al RecyclerViewAdapter
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Film film) {
                        //Cosas
                        Toast.makeText(getContext(), film.getTitle(), Toast.LENGTH_LONG).show();
                    }
                });

                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
