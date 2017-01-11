package com.and119_idi.filmflow.model;

import java.io.Serializable;

public interface SearchOptionStrategy extends Serializable {
    String getColumn();

    int getWithFilterSeparatorResource();

    int getHintResource();
}
