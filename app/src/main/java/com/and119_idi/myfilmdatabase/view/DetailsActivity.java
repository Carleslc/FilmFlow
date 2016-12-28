package com.and119_idi.myfilmdatabase.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

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
        ((TextView) findViewById(R.id.details_film_description)).setText(mFilm.getDescription());
        (findViewById(R.id.details_delete_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

        mRatingBar = (RatingBar) findViewById(R.id.details_rating_bar);
        mRatingBar.setRating(mFilm.getCriticsRate() / 2f);
    }

    private void initAlertDialog() {

        alertDialog = new AlertDialog.Builder(DetailsActivity.this).create();
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Are you sure you want to delete the film? \nDeleted films can't be recovered");
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFilm();
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void initInfoDialog() {
        alertDialog =  new AlertDialog.Builder(DetailsActivity.this).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage("Film deleted!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DetailsActivity.super.fi();
                    }
                });

        alertDialog.show();
    }

    private void deleteFilm() {
        FilmData filmData = new FilmData(DetailsActivity.this);
        filmData.open();
        filmData.deleteFilm(mFilm);
        filmData.close();
        initInfoDialog();
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
