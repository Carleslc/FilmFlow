package com.and119_idi.filmflow.view;

import android.support.annotation.NonNull;

import com.and119_idi.filmflow.controller.FilmsDetailedRecyclerViewAdapter;
import com.and119_idi.filmflow.controller.FilmsRecyclerViewAdapter;

/**
 * Created by albert on 29/11/16.
 * Refactored by Carlos on 25/12/16.
 */
public class DetailedFilmsFragment extends MainFilmsFragment {

    @Override
    @NonNull
    protected FilmsRecyclerViewAdapter getFilmsRecyclerViewAdapter() {
        return new FilmsDetailedRecyclerViewAdapter();
    }

}
