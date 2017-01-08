package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;
import com.and119_idi.myfilmdatabase.model.FilmData;

/**
 * Created by albert on 8/12/16.
 */
public class AddFilmActivityNext extends CheckableDialogActivity {

    private EditText descriptionEditText;
    private RatingBar ratingBar;
    private Film newFilm;
    private FilmData filmData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_next_layout);
        init();
    }

    private void init() {
        descriptionEditText = (EditText) findViewById(R.id.description);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> fi());

        filmData = new FilmData(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        newFilm = (Film) bundle.getSerializable(getString(R.string.bundle_film_id));

        unsavedData = true;
    }

    public void commitFilm(View view) {
        if (checkData(true)) {
            completeFilm();
            filmData.open();
            filmData.addFilm(newFilm);
            filmData.close();
            Toast.makeText(AddFilmActivityNext.this, R.string.film_added, Toast.LENGTH_LONG).show();
            unsavedData = false;
            fi();
        }
    }

    private void completeFilm() {
        newFilm.setDescription(descriptionEditText.getText().toString());
        newFilm.setCriticsRate(Math.round(ratingBar.getRating() * 2));
        Log.d("AddFilmActivityNext", newFilm.getTitle() + " rate: "
                + String.valueOf(newFilm.getCriticsRate()));
    }

    @Override
    protected boolean checkData(boolean showErrors) {
        boolean ret = true;

        if (isEmpty(descriptionEditText)) {
            if (showErrors) descriptionEditText.setError(getString(R.string.enter_description));
            ret = false;
        }

        return ret;
    }
}
