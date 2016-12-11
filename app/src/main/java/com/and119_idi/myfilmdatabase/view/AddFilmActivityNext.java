package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.and119_idi.myfilmdatabase.model.FilmData;
import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 8/12/16.
 */
public class AddFilmActivityNext extends DialogActivity {

    private EditText descriptionEditText;
    private RatingBar ratingBar;
    private Film newFilm;
    private FilmData filmData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_next_layout);
        setFinishOnTouchOutside(false);
        init();
    }

    private void init() {
        descriptionEditText = (EditText) findViewById(R.id.description);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        filmData = new FilmData(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        newFilm = (Film) bundle.getSerializable("newFilm");
    }

    public void commitFilm(View view) {
        if (checkBeforeFinish()) {
            completeFilm();
            filmData.addFilm(newFilm);
        }

    }

    private void completeFilm() {
        newFilm.setDescription(descriptionEditText.getText().toString());
        newFilm.setCritics_rate(Math.round(ratingBar.getRating()));
        Log.d("Rating",String.valueOf(newFilm.getCritics_rate()));
    }

    @Override
    protected boolean checkBeforeFinish() {
        Boolean ret = true;

        if (isEmpty(descriptionEditText)) {
            descriptionEditText.setError("Enter a description");
            ret = false;
        }

        return ret;
    }
}
