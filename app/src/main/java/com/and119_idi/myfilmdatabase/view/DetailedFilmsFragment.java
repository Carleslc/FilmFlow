package com.and119_idi.myfilmdatabase.view;

import android.support.annotation.NonNull;

import com.and119_idi.myfilmdatabase.controller.FilmsDetailedRecyclerViewAdapter;
import com.and119_idi.myfilmdatabase.controller.FilmsRecyclerViewAdapter;

/**
 * Created by albert on 29/11/16.
 */
public class DetailedFilmsFragment extends MainFilmsFragment {

    @Override
    @NonNull
    protected FilmsRecyclerViewAdapter getFilmsRecyclerViewAdapter() {
        return new FilmsDetailedRecyclerViewAdapter();
    }

}
