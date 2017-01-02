package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

/**
 * Created by Carlos Lázaro Costa on 26/12/16.
 */

//Habría que hacer algo con esos dialogs? No podia reusarlo ni ponerlo en DialogActivity...
public class DetailsActivity extends DialogActivity {

    private Film mFilm;
    private RatingBar mRatingBar;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        initAlertDialog();

        mFilm = (Film) getIntent().getExtras().getSerializable("film");
        ((TextView) findViewById(R.id.details_film_title)).setText(mFilm.getTitle());
        ((TextView) findViewById(R.id.details_film_director)).setText(mFilm.getDirector());
        ((TextView) findViewById(R.id.details_film_actor)).setText(mFilm.getProtagonist());
        ((TextView) findViewById(R.id.details_film_year)).setText(String.valueOf(mFilm.getYear()));
        ((TextView) findViewById(R.id.details_film_country)).setText(mFilm.getCountry());
        Toast.makeText(this, "Description: " + mFilm.getDescription(), Toast.LENGTH_LONG).show();
        ((TextView) findViewById(R.id.details_film_description)).setText(mFilm.getDescription());
        (findViewById(R.id.details_delete_button)).setOnClickListener((v) -> alertDialog.show());

        mRatingBar = (RatingBar) findViewById(R.id.details_rating_bar);
        mRatingBar.setRating(mFilm.getCriticsRate() / 2f);
    }

    private void initAlertDialog() {
        alertDialog = new AlertDialog.Builder(DetailsActivity.this).create();
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Are you sure you want to delete the film? \nDeleted films can't be recovered");
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    deleteFilm();
                    dialog.dismiss();
                }
        );
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.dismiss());
    }

    private void deleteFilm() {
        performOnFilmData((filmData) -> filmData.deleteFilm(mFilm));
        Toast.makeText(this, "Film deleted!", Toast.LENGTH_LONG).show();
    }

    protected void fi() {
        mFilm.setCriticsRate(Math.round(mRatingBar.getRating() * 2));
        performOnFilmData((filmData) -> filmData.addFilm(mFilm));
        super.fi();
    }

    private void performOnFilmData(FilmDataPerformer performer) {
        FilmData filmData = new FilmData(DetailsActivity.this);
        filmData.open();
        performer.performOnFilmData(filmData);
        filmData.close();
    }

    @FunctionalInterface
    private interface FilmDataPerformer {
        void performOnFilmData(@NonNull FilmData filmData);
    }

}
