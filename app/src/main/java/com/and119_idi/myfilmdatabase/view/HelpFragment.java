package com.and119_idi.myfilmdatabase.view;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by albert and Carlos on 24/12/16.
 */

public class HelpFragment extends Fragment {

    private TextView mshowFilms;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.help_layout, container, false);
        init(ret);
        return ret;
    }


    private void init(View view) {
        mshowFilms = (TextView) view.findViewById(R.id.show_films);
        mshowFilms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("KEK");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        FloatingActionButton activityfab = (FloatingActionButton) getActivity().findViewById(R.id.add_films_button);
        activityfab.hide();
        super.onActivityCreated(savedInstanceState);
    }
}
