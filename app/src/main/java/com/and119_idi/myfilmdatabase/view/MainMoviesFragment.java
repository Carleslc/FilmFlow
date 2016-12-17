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

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.controller.MoviesSimpleRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.OnItemClickListener;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

import java.util.List;

/**
 * Created by Carlos Lázaro Costa on 11/12/16.
 */
public class MainMoviesFragment extends Fragment {

    private List<Film> moviesList;
    private RecyclerView mRecyclerView;
    private MoviesSimpleRecyclerViewAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_movies, container, false);

        mRecyclerView = (RecyclerView) ret.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) ret.findViewById(R.id.progress_bar);

        new FetchFilmsTask().execute();

        return ret;
    }

    //Creamos una AsyncTask para usar la base de datos
    public class FetchFilmsTask extends AsyncTask<Void, Void, Boolean> {

        private FilmData filmData;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                filmData = new FilmData(getContext());
                filmData.open();
                moviesList = filmData.getAllFilms();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean succeed) {
            if (succeed) {
                adapter = new MoviesSimpleRecyclerViewAdapter(moviesList);

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
            progressBar.setVisibility(View.GONE);
        }
    }

}
