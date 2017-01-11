package com.and119_idi.filmflow.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.and119_idi.filmflow.R;
import com.and119_idi.filmflow.model.Film;
import com.and119_idi.filmflow.model.FilmData;

/**
 * Created by Albert and Carlos on 26/12/16.
 */
public class DetailsActivity extends DialogActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private Film mFilm;
    private float mShowingStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        mFilm = (Film) getIntent().getExtras().getSerializable(getString(R.string.bundle_film_id));

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
     * Created by albert on 03/07/17.
     */
    private void initDeleteDialog() {
        ConfirmDialog deleteDialog = new ConfirmDialog(DetailsActivity.this,
                getString(R.string.delete_warning),
                (dialog, which) -> {
                    deleteFilm();
                    dialog.dismiss();
                    Toast.makeText(DetailsActivity.this, R.string.film_deleted, Toast.LENGTH_LONG).show();
                    fi();
                },
                (dialog, which) -> dialog.dismiss());
        deleteDialog.create();

        (findViewById(R.id.details_delete_button)).setOnClickListener(v -> deleteDialog.show());
    }

    /**
     * Created by Carlos on 08/07/17.
     */
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
                        getString(R.string.change_rating_from) +
                                mFilm.getCriticsRate() + getString(R.string.to_separator) + newRating + "?",
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
     * Created by albert on 03/07/17.
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
