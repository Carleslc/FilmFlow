package com.and119_idi.filmflow.view;

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

import com.and119_idi.filmflow.R;
import com.and119_idi.filmflow.controller.FilmsRecyclerViewAdapter;
import com.and119_idi.filmflow.model.Film;
import com.and119_idi.filmflow.model.FilmData;
import com.and119_idi.filmflow.model.SearchOptionStrategy;

import java.util.List;

/**
 * Created by Carlos Lázaro Costa on 11/12/16.
 */
public class MainFilmsFragment extends Fragment {

    private static final String TAG = MainFilmsFragment.class.getSimpleName();

    private static final int DETAILS_ACTIVITY_RESULT_CODE = 1;
    private static final int ADD_FILM_RESULT_CODE = 2;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FilmsRecyclerViewAdapter mFilmsRecyclerViewAdapter;
    private FloatingActionButton mFloatingActionButton;
    private List<Film> mFilmList;
    private String mFilter;
    private SearchOptionStrategy mSearchOption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_films, container, false);

        mRecyclerView = (RecyclerView) ret.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.swipeRefreshLayout);
        mFloatingActionButton = (FloatingActionButton) ret.findViewById(R.id.add_films_button);
        initListeners();
        mFilter = null;
        mSearchOption = null;
        refreshFilms();

        return ret;
    }

    private void initListeners() {
        mFloatingActionButton.setOnClickListener((v) ->
                startActivityForResult(new Intent(getContext(), AddFilmActivity.class), ADD_FILM_RESULT_CODE)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshFilms);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    protected FilmsRecyclerViewAdapter getFilmsRecyclerViewAdapter() {
        return new FilmsRecyclerViewAdapter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshFilms();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshFilms() {
        new FetchFilmsTask().execute();
    }

    private void refreshFilmsWithListener(@Nullable OnRefreshFilmsListener onRefreshListener) {
        new FetchFilmsTask(onRefreshListener).execute();
    }

    public void refreshFilmsWithFilter(@Nullable String filter, @Nullable SearchOptionStrategy searchOption,
                                       @Nullable OnRefreshFilmsListener onRefreshListener) {
        if (filter != null) filter = filter.trim();
        mSearchOption = searchOption;
        mFilter = filter;
        refreshFilmsWithListener(onRefreshListener);
    }

    public interface OnRefreshFilmsListener {
        void onRefreshFilms(int filmsSize);
    }

    /**
     * Created by albert on 29/11/16.
     * Refactored by Carlos on 25/12/16.
     */
    private class FetchFilmsTask extends AsyncTask<Void, Void, Boolean> {

        private FilmData mFilmData;
        private
        @Nullable
        OnRefreshFilmsListener mOnRefreshFilmsListener;

        FetchFilmsTask() {
            this(null);
        }

        FetchFilmsTask(@Nullable OnRefreshFilmsListener onRefreshFilmsListener) {
            mOnRefreshFilmsListener = onRefreshFilmsListener;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mFilmData = new FilmData(getContext());
                mFilmData.open();
                mFilmList = mFilmData.getAllFilms(mFilter, mSearchOption);
                mFilmData.close();
                return true;
            } catch (Exception e) {
                Log.e(TAG, getString(R.string.failed_fetching_data), e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(getContext(),
                        getString(R.string.failed_fetching_data), Toast.LENGTH_SHORT).show();
            } else {
                if (mFilmsRecyclerViewAdapter == null) {
                    mFilmsRecyclerViewAdapter = getFilmsRecyclerViewAdapter();
                    mFilmsRecyclerViewAdapter.setOnItemClickListener((film) -> {
                                if (film != null) {
                                    Log.d(TAG, "Received film ID-" + film.getId() + ": " + film.getTitle()
                                            + " with description: " + film.getDescription());
                                    startActivityForResult(
                                            new Intent(getContext(), DetailsActivity.class)
                                                    .putExtra(getString(R.string.bundle_film_id), film),
                                            DETAILS_ACTIVITY_RESULT_CODE
                                    );
                                }
                            }
                    );
                    mRecyclerView.setAdapter(mFilmsRecyclerViewAdapter);
                }
                mFilmsRecyclerViewAdapter.setFilms(mFilmList);
                if (mOnRefreshFilmsListener != null) {
                    mOnRefreshFilmsListener.onRefreshFilms(mFilmList.size());
                }
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
