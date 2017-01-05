package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

/**
 * Created by Carlos Lázaro Costa on 26/12/16.
 */
public class DetailsActivity extends DialogActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private Film mFilm;
    private float mShowingStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        mFilm = (Film) getIntent().getExtras().getSerializable("film");

        initDeleteDialog();
        initRatingDialog();

        ((TextView) findViewById(R.id.details_film_title)).setText(mFilm.getTitle());
        ((TextView) findViewById(R.id.details_film_director)).setText(mFilm.getDirector());
        ((TextView) findViewById(R.id.details_film_actor)).setText(mFilm.getProtagonist());
        ((TextView) findViewById(R.id.details_film_year)).setText(String.valueOf(mFilm.getYear()));
        ((TextView) findViewById(R.id.details_film_country)).setText(mFilm.getCountry());
        ((TextView) findViewById(R.id.details_film_description)).setText(mFilm.getDescription());
        (findViewById(R.id.details_back_button)).setOnClickListener(v -> fi());
    }

    /**
     * Created by albert on 3/7/17.
     */
    private void initDeleteDialog() {
        ConfirmDialog deleteDialog = new ConfirmDialog(DetailsActivity.this,
                "Are you sure you want to delete the film?\nDeleted films can't be recovered.",
                (dialog, which) -> {
                    deleteFilm();
                    dialog.dismiss();
                    Toast.makeText(DetailsActivity.this, "Film deleted!", Toast.LENGTH_LONG).show();
                    fi();
                },
                (dialog, which) -> dialog.dismiss());
        deleteDialog.create();

        (findViewById(R.id.details_delete_button)).setOnClickListener(v -> deleteDialog.show());
    }

    private void initRatingDialog() {
        RatingBar ratingBar = (RatingBar) findViewById(R.id.details_rating_bar);
        mShowingStars = mFilm.getCriticsRate() / 2f;
        ratingBar.setIsIndicator(false);
        ratingBar.setRating(mShowingStars);

        ratingBar.setOnRatingBarChangeListener((bar, stars, fromUser) -> {
            if (fromUser && stars != mShowingStars) {
                int newRating = Math.round(stars * 2);
                ratingBar.setRating(stars);
                ConfirmDialog ratingDialog = new ConfirmDialog(DetailsActivity.this,
                        "Do you want to change the film rating from " +
                                mFilm.getCriticsRate() + " to " + newRating + "?",
                        (dialog, which) -> {
                            Log.d(TAG, "Changing " + mFilm.getTitle() + " ID-" + mFilm.getId()
                                    + " rating to " + newRating);
                            mFilm.setCriticsRate(newRating);
                            mShowingStars = stars;
                            performOnFilmData((filmData) -> {
                                Log.d(TAG, "performOnFilmData callback: add " + mFilm.getId());
                                filmData.addFilm(mFilm);
                            });
                            dialog.dismiss();
                        },
                        (dialog, which) -> dialog.dismiss());
                ratingDialog.setOnDismissListener(dialog -> ratingBar.setRating(mShowingStars));
                ratingDialog.create();
                ratingDialog.show();
            }
        });
    }

    /**
     * Created by albert on 3/7/17.
     */
    private void deleteFilm() {
        performOnFilmData((filmData) -> {
            Log.d(TAG, "performOnFilmData callback: delete " + mFilm.getId());
            filmData.deleteFilm(mFilm);
        });
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
