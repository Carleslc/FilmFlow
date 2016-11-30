package com.and119_idi.myfilmdatabase;

/**
 * Created by albert on 2/11/16.
 *
 * Interface para poder poner un listener a las pelis en el recyclerview
 * Será implementado desde MoviesFragment y pasado al RecyclerViewAdapter
 */

public interface OnItemClickListener {
    void onItemClick(Film item);
}
