package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

/**
 * Created by Carlos Lázaro Costa on 26/12/16.
 */
public class DetailsActivity extends DialogActivity {

    private Film mFilm;
    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        mFilm = (Film) getIntent().getExtras().getSerializable("film");
        ((TextView) findViewById(R.id.details_film_title)).setText(mFilm.getTitle());
        ((TextView) findViewById(R.id.details_film_director)).setText(mFilm.getDirector());
        ((TextView) findViewById(R.id.details_film_actor)).setText(mFilm.getProtagonist());
        ((TextView) findViewById(R.id.details_film_year)).setText(String.valueOf(mFilm.getYear()));
        ((TextView) findViewById(R.id.details_film_country)).setText(mFilm.getCountry());
        ((TextView) findViewById(R.id.details_film_description)).setText(mFilm.getDescription());
        mRatingBar = (RatingBar) findViewById(R.id.details_rating_bar);
        mRatingBar.setRating(mFilm.getCriticsRate() / 2f);
    }

    protected void fi() {
        mFilm.setCriticsRate(Math.round(mRatingBar.getRating() * 2));
        FilmData filmData = new FilmData(DetailsActivity.this);
        filmData.open();
        filmData.addFilm(mFilm);
        filmData.close();
        super.fi();
    }

}
