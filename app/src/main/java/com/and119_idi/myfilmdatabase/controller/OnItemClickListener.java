package com.and119_idi.myfilmdatabase.controller;

import com.and119_idi.myfilmdatabase.model.Film;

/**
 * Created by albert on 2/11/16.
 *
 * Interface per a poder posar un listener a les pel·lícules en el RecyclerView
 * Será implementat desde el fragment MainFilmsFragment per a fer-lo servir al RecyclerViewAdapter
 *
 */
@FunctionalInterface
public interface OnItemClickListener {
    void onItemClick(Film item);
}
