package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 6/12/16.
 */
public class AddFilmActivity extends CheckableDialogActivity {

    private EditText titleEditText;
    private EditText yearEditText;
    private EditText directorEditText;
    private EditText actorEditText;
    private Film newFilm;

    private AutoCompleteTextView autoCompleteTextView;
    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            unsavedData = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_layout);
        init();
    }

    private void init() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.country);
        titleEditText = (EditText) findViewById(R.id.title);
        yearEditText = (EditText) findViewById(R.id.year);
        directorEditText = (EditText) findViewById(R.id.director);
        actorEditText = (EditText) findViewById(R.id.actor);
        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener((v) -> fi());

        autoCompleteTextView.addTextChangedListener(filterTextWatcher);
        titleEditText.addTextChangedListener(filterTextWatcher);
        yearEditText.addTextChangedListener(filterTextWatcher);
        directorEditText.addTextChangedListener(filterTextWatcher);
        actorEditText.addTextChangedListener(filterTextWatcher);

        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        autoCompleteTextView.setAdapter(adapter);

        initAlertDialog();
    }

    @Override
    protected boolean checkData(boolean showErrors) {
        boolean ret = true;

        if (isEmpty(titleEditText)) {
            if (showErrors)titleEditText.setError("Enter a title");
            ret = false;
        }
        if (isEmpty(autoCompleteTextView)) {
            if (showErrors)autoCompleteTextView.setError("Enter a Country");
            ret = false;
        }
        // validate yearEditText contains only numbers (at least 1)
        if (!yearEditText.getText().toString().matches("^[0-9]+$")) {
            if (showErrors)yearEditText.setError("Enter a year");
            ret = false;
        }

        if (isEmpty(directorEditText)) {
            if (showErrors)directorEditText.setError("Enter a director");
            ret = false;
        }

        if (isEmpty(actorEditText)) {
            if (showErrors)actorEditText.setError("Enter a Actor");
            ret = false;
        }

        return ret;
    }

    private void createFilm() {
        newFilm = new Film(
                titleEditText.getText().toString(),
                directorEditText.getText().toString(),
                autoCompleteTextView.getText().toString(),
                Integer.valueOf(yearEditText.getText().toString()),
                actorEditText.getText().toString()
        );
    }

    public void continueAdding(View view) {
        if (checkData(true)) {
            createFilm();
            Bundle bundle = new Bundle();
            bundle.putSerializable("newFilm", newFilm);
            Intent intent = new Intent(this, AddFilmActivityNext.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

}
