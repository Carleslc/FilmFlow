package com.and119_idi.myfilmdatabase;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * Created by albert on 6/12/16.
 */

public class AddFilmActivity extends Activity {

    private AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_layout);
        findThemAll();


    }

    private void findThemAll() {
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.country);

        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        autoCompleteTextView.setAdapter(adapter);

    }

}
