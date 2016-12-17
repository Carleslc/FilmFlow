package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 6/12/16.
 */
public class AddFilmActivity extends DialogActivity {

    private EditText titleEditText;
    private EditText countryEditText;
    private EditText yearEditText;
    private EditText directorEditText;
    private EditText actorEditText;
    private Film newFilm;

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_layout);
        setFinishOnTouchOutside(false);
        init();
    }

    private void init() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.country); // FIXME
        titleEditText = (EditText) findViewById(R.id.title);
        countryEditText = (EditText) findViewById(R.id.country);
        yearEditText = (EditText) findViewById(R.id.year);
        directorEditText = (EditText) findViewById(R.id.director);
        actorEditText = (EditText) findViewById(R.id.actor);

        
        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        autoCompleteTextView.setAdapter(adapter);

        initAlertDialog();
    }

    @Override
    protected boolean checkBeforeFinish() {
        boolean ret = true;

        if (isEmpty(titleEditText)) {
            titleEditText.setError("Enter a title");
            ret = false;
        }
        if (isEmpty(countryEditText)) {
            countryEditText.setError("Enter a Country");
            ret = false;
        }
        if (isEmpty(yearEditText)) {
            yearEditText.setError("Enter a year");
            ret = false;
        }

        if (isEmpty(directorEditText)) {
            directorEditText.setError("Enter a director");
            ret = false;
        }

        if (isEmpty(actorEditText)) {
            actorEditText.setError("Enter a Actor");
            ret = false;
        }

        return ret;
    }

    @Override
    public void showDialog(View view) {
        super.showDialog(view);
    }

    private void createFilm() {
        newFilm = new Film();
        newFilm.setTitle(titleEditText.getText().toString());
        newFilm.setCountry(countryEditText.getText().toString());
        newFilm.setYear(Integer.valueOf(yearEditText.getText().toString()));
        newFilm.setDirector(directorEditText.getText().toString());
        newFilm.setProtagonist(actorEditText.getText().toString());
    }

    public void continueAdding(View view) {
        if (checkBeforeFinish()) {
            createFilm();
            Bundle bundle = new Bundle();
            bundle.putSerializable("newFilm", newFilm);
            Intent intent = new Intent(this, AddFilmActivityNext.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(bundle);
            startActivity(intent);
            fi();
        }
    }

}
