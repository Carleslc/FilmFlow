package com.and119_idi.filmflow.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.and119_idi.filmflow.R;
import com.and119_idi.filmflow.controller.MyHelpListAdapter;

/**
 * Created by Albert and Carlos on 24/12/16.
 */
public class HelpFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.help_layout, container, false);
        init(ret);
        return ret;
    }

    private void init(View view) {
        ExpandableListView epView = (ExpandableListView) view.findViewById(R.id.help_sections);
        epView.setAdapter(new MyHelpListAdapter(getActivity()));
    }
}
