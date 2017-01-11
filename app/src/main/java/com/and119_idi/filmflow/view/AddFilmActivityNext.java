package com.and119_idi.filmflow.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.and119_idi.filmflow.R;
import com.and119_idi.filmflow.model.Film;
import com.and119_idi.filmflow.model.FilmData;

/**
 * Created by albert on 8/12/16.
 */
public class AddFilmActivityNext extends CheckableDialogActivity {

    private EditText mDescriptionEditText;
    private RatingBar mRatingBar;
    private Film mNewFilm;
    private FilmData mFilmData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_next_layout);
        init();
    }

    private void init() {
        mDescriptionEditText = (EditText) findViewById(R.id.description);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);

        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> fi());

        mFilmData = new FilmData(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mNewFilm = (Film) bundle.getSerializable(getString(R.string.bundle_film_id));

        mUnsavedData = true;
    }

    public void commitFilm(View view) {
        if (checkData(true)) {
            completeFilm();
            mFilmData.open();
            mFilmData.addFilm(mNewFilm);
            mFilmData.close();
            Toast.makeText(AddFilmActivityNext.this, R.string.film_added, Toast.LENGTH_LONG).show();
            mUnsavedData = false;
            fi();
        }
    }

    private void completeFilm() {
        mNewFilm.setDescription(mDescriptionEditText.getText().toString());
        mNewFilm.setCriticsRate(Math.round(mRatingBar.getRating() * 2));
        Log.d("AddFilmActivityNext", mNewFilm.getTitle() + " rate: "
                + String.valueOf(mNewFilm.getCriticsRate()));
    }

    @Override
    protected boolean checkData(boolean showErrors) {
        boolean ret = true;

        if (isEmpty(mDescriptionEditText)) {
            if (showErrors) mDescriptionEditText.setError(getString(R.string.enter_description));
            ret = false;
        }

        return ret;
    }
}
