package com.and119_idi.myfilmdatabase;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by albert on 4/12/16.
 */

public class TestingMoviesFragmentActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_layout);

        loadMoviesFragment();
    }

    private void loadMoviesFragment() {

        MoviesFragment fragment = new MoviesFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.moviesFragment_placeholder, fragment);
        ft.commit();
    }

}
