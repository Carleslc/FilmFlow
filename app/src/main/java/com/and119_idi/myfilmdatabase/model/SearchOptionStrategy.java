package com.and119_idi.myfilmdatabase.model;

import java.io.Serializable;

public interface SearchOptionStrategy extends Serializable {
    String getColumn();

    int getWithFilterSeparatorResource();

    int getHintResource();
}
