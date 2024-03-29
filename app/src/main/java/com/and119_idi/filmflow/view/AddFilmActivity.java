package com.and119_idi.filmflow.view;

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

import com.and119_idi.filmflow.R;
import com.and119_idi.filmflow.model.CountryList;
import com.and119_idi.filmflow.model.Film;

/**
 * Created by albert on 6/12/16.
 */
public class AddFilmActivity extends CheckableDialogActivity {

    private EditText mTitleEditText;
    private EditText mYearEditText;
    private EditText mDirectorEditText;
    private EditText mActorEditText;
    private Film mNewFilm;

    private AutoCompleteTextView mCountryAutoCompleteTextView;
    private TextWatcher mFilterTextWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_layout);
        init();
    }

    private void init() {
        mCountryAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.country);
        mTitleEditText = (EditText) findViewById(R.id.title);
        mYearEditText = (EditText) findViewById(R.id.year);
        mDirectorEditText = (EditText) findViewById(R.id.director);
        mActorEditText = (EditText) findViewById(R.id.actor);
        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener((v) -> fi());


        initTextWatcher();
        mCountryAutoCompleteTextView.addTextChangedListener(mFilterTextWatcher);
        mTitleEditText.addTextChangedListener(mFilterTextWatcher);
        mYearEditText.addTextChangedListener(mFilterTextWatcher);
        mDirectorEditText.addTextChangedListener(mFilterTextWatcher);
        mActorEditText.addTextChangedListener(mFilterTextWatcher);


        ArrayAdapter<String> suggestionAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CountryList.get());
        mCountryAutoCompleteTextView.setAdapter(suggestionAdapter);

        initAlertDialog();
    }

    private void initTextWatcher() {
        mFilterTextWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    mUnsavedData = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }


    @Override
    protected boolean checkData(boolean showErrors) {
        boolean ret = true;

        if (isEmpty(mTitleEditText)) {
            if (showErrors) mTitleEditText.setError(getString(R.string.enter_title));
            ret = false;
        }
        if (isEmpty(mCountryAutoCompleteTextView)) {
            if (showErrors)
                mCountryAutoCompleteTextView.setError(getString(R.string.enter_country));
            ret = false;
        }
        // validate mYearEditText contains 4 numbers that does not start with 0
        if (!mYearEditText.getText().toString().matches("^[1-9][0-9]{3}$")) {
            if (showErrors) mYearEditText.setError(getString(R.string.enter_year));
            ret = false;
        }

        if (isEmpty(mDirectorEditText)) {
            if (showErrors) mDirectorEditText.setError(getString(R.string.enter_director));
            ret = false;
        }

        if (isEmpty(mActorEditText)) {
            if (showErrors) mActorEditText.setError(getString(R.string.enter_actor));
            ret = false;
        }

        return ret;
    }

    private void createFilm() {
        mNewFilm = new Film(
                mTitleEditText.getText().toString(),
                mDirectorEditText.getText().toString(),
                mCountryAutoCompleteTextView.getText().toString(),
                Integer.valueOf(mYearEditText.getText().toString()),
                mActorEditText.getText().toString()
        );
    }

    public void continueAdding(View view) {
        if (checkData(true)) {
            createFilm();
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.bundle_film_id), mNewFilm);
            Intent intent = new Intent(this, AddFilmActivityNext.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

}
