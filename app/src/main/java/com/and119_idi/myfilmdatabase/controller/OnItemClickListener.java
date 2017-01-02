package com.and119_idi.myfilmdatabase.controller;

import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 2/11/16.
 *
 * Interface para poder poner un listener a las pelis en el recyclerview
 * Será implementado desde MoviesFragment y pasado al RecyclerViewAdapter
 */
@FunctionalInterface
public interface OnItemClickListener {
    void onItemClick(Film item);
}
