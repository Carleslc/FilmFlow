package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by Carlos Lázaro Costa on 26/12/16.
 */
public class DetailsActivity extends DialogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        Film film = (Film) getIntent().getExtras().getSerializable("film");
        ((TextView) findViewById(R.id.details_film_title)).setText(film.getTitle());
        ((TextView) findViewById(R.id.details_film_director)).setText(film.getDirector());
        ((TextView) findViewById(R.id.details_film_actor)).setText(film.getProtagonist());
        ((TextView) findViewById(R.id.details_film_year)).setText(String.valueOf(film.getYear()));
        ((TextView) findViewById(R.id.details_film_country)).setText(film.getCountry());
        ((TextView) findViewById(R.id.details_film_description)).setText(film.getDescription());
        ((RatingBar) findViewById(R.id.details_rating_bar)).setRating(film.getCriticsRate() / 2f);
    }

}
