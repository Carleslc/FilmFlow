package com.and119_idi.filmflow.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pr_idi on 10/11/16.
 * Modified by Carlos on 26/12/16 and 08/01/17.
 */
public class FilmData implements Closeable {

    private static final String TAG = FilmData.class.getSimpleName();

    // Database fields
    private SQLiteDatabase mDatabase;
    private MySQLiteHelper mDatabaseHelper;

    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE,
            MySQLiteHelper.COLUMN_COUNTRY,
            MySQLiteHelper.COLUMN_YEAR_RELEASE,
            MySQLiteHelper.COLUMN_DIRECTOR,
            MySQLiteHelper.COLUMN_PROTAGONIST,
            MySQLiteHelper.COLUMN_CRITICS_RATE,
            MySQLiteHelper.COLUMN_DESCRIPTION
    };

    public FilmData(Context context) {
        mDatabaseHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mDatabaseHelper.checkVersion(mDatabase);
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        int rows = mDatabase.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
        Log.d(TAG, "Film deleted with id: " + id + ((rows > 0) ? " (SUCCESS)" : "(FAIL)"));
    }

    private boolean exists(@NonNull Film film) {
        return mDatabase.query(MySQLiteHelper.TABLE_FILMS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + film.getId(), null, null, null, null)
                .getCount() > 0;
    }

    public void addFilm(@NonNull Film film) {
        if (exists(film)) updateFilm(film);
        else MySQLiteHelper.insertNewFilm(mDatabase, film);
    }

    private void updateFilm(@NonNull Film film) {
        mDatabase.update(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.filmToValues(film),
                MySQLiteHelper.COLUMN_ID + " = " + film.getId(), null);
        Log.d(TAG, "updateFilm " + film.getTitle() + " ID-" + film.getId()
                + " with description " + film.getDescription());
    }

    public List<Film> getAllFilms(@Nullable String filter, @Nullable SearchOptionStrategy searchOption) {
        List<Film> films = new ArrayList<>();

        filter = (filter != null && searchOption != null) ?
                searchOption.getColumn() + " LIKE '%" + filter + "%'" : null;

        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, filter, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film film = cursorToFilm(cursor);
            films.add(film);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return films;
    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film(
                cursor.getString(1),
                cursor.getString(4),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(5));
        film.setId(cursor.getLong(0));
        film.setCriticsRate(cursor.getInt(6));
        film.setDescription(cursor.getString(7));
        return film;
    }

}
