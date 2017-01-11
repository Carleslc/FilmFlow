package com.and119_idi.filmflow.model;

import com.and119_idi.filmflow.R;

public class SearchByActorOption implements SearchOptionStrategy {

    @Override
    public String getColumn() {
        return MySQLiteHelper.COLUMN_PROTAGONIST;
    }

    @Override
    public int getWithFilterSeparatorResource() {
        return R.string.with_actor;
    }

    @Override
    public int getHintResource() {
        return R.string.search_by_actor_hint;
    }
}
