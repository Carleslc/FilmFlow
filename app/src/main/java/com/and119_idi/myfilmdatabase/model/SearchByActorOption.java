package com.and119_idi.myfilmdatabase.model;

import com.and119_idi.myfilmdatabase.R;

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
