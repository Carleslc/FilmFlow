package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by albert on 23/12/16.
 */
public class AboutActivity extends DialogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
    }

    @Override
    protected boolean checkData(boolean showErrors) {
        return true;
    }
}
