package com.and119_idi.myfilmdatabase;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by albert on 8/12/16.
 */
public class AddFilmActivityNext extends DialogActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_film_next_layout);
        setFinishOnTouchOutside(false);
    }

    @Override
    protected boolean checkBeforeClosing() {
        return false;
    }
}
