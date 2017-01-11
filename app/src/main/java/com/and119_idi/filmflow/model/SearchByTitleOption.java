package com.and119_idi.filmflow.model;

import com.and119_idi.filmflow.R;

public class SearchByTitleOption implements SearchOptionStrategy {

    @Override
    public String getColumn() {
        return MySQLiteHelper.COLUMN_TITLE;
    }

    @Override
    public int getWithFilterSeparatorResource() {
        return R.string.with_title;
    }

    @Override
    public int getHintResource() {
        return R.string.search_by_title_hint;
    }
}
