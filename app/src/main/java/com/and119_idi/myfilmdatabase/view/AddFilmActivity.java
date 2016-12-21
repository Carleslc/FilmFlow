package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 6/12/16.
 */
public class AddFilmActivity extends DialogActivity {

    private EditText titleEditText;
    private EditText yearEditText;
    private EditText directorEditText;
    private EditText actorEditText;
    private Film newFilm;
    private ImageButton cancel;

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_layout);
        setFinishOnTouchOutside(false);
        init();
    }

    private void init() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.country);
        titleEditText = (EditText) findViewById(R.id.title);
        yearEditText = (EditText) findViewById(R.id.year);
        directorEditText = (EditText) findViewById(R.id.director);
        actorEditText = (EditText) findViewById(R.id.actor);
        cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertAndFinish();
            }
        });

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
        if (isEmpty(yearEditText)) {
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

    @Override
    public void showDialog(View view) {
        super.showDialog(view);
    }

    private void createFilm() {
        newFilm = new Film();
        newFilm.setTitle(titleEditText.getText().toString());
        newFilm.setCountry(autoCompleteTextView.getText().toString());
        newFilm.setYear(Integer.valueOf(yearEditText.getText().toString()));
        newFilm.setDirector(directorEditText.getText().toString());
        newFilm.setProtagonist(actorEditText.getText().toString());
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
            fi();
        }
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            unsavedData = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };


}
