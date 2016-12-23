package com.and119_idi.myfilmdatabase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by albert on 23/12/16.
 */

public class AboutActivity extends DialogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutlayout);
    }



    @Override
    protected boolean checkData(boolean showErrors) {
        return true;
    }
}
