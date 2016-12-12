package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by Carlos Lázaro Costa on 11/12/16.
 */
public class MainMoviesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_movies, container, false);
    }
}
