package com.and119_idi.myfilmdatabase.view;

import android.os.AsyncTask;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.controller.MoviesRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.OnItemClickListener;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

import java.util.List;

/**
 * Created by albert on 29/11/16.
 */
public class MoviesFragment extends MainMoviesFragment {

    private MoviesRecyclerViewAdapter adapter;

    protected void getFilms() {
        new FetchFilmsTask().execute();
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
                        Toast.makeText(getContext(), film.getTitle(), Toast.LENGTH_SHORT).show();
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
