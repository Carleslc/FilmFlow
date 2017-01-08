package com.and119_idi.myfilmdatabase.model;

import android.content.ContentValues;
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

    // FIXME Change with a method inserting default films when mDatabase is empty
    public Film createFilm(String title, String director) {
        ContentValues values = new ContentValues();
        Log.d(TAG, "Creating " + title + " " + director);

        // Add data: Note that this method only provides title and director
        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, director);

        // Invented data
        values.put(MySQLiteHelper.COLUMN_COUNTRY, "Catalonia");
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, 2014);
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, "Do not know");
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, 5);

        // Actual insertion of the data using the values variable
        long insertId = mDatabase.insert(MySQLiteHelper.TABLE_FILMS, null, values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the mDatabase
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        int rows = mDatabase.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
        Log.d(TAG, "Film deleted with id: " + id + ((rows > 0) ? " (SUCCESS)" : "(FAIL)"));
    }

    private ContentValues filmToValues(@NonNull Film film) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_TITLE, film.getTitle());
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, film.getDirector());
        values.put(MySQLiteHelper.COLUMN_COUNTRY, film.getCountry());
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, film.getYear());
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, film.getProtagonist());
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, film.getCriticsRate());
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, film.getDescription());

        return values;
    }

    private boolean exists(@NonNull Film film) {
        return mDatabase.query(MySQLiteHelper.TABLE_FILMS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + film.getId(), null, null, null, null)
                .getCount() > 0;
    }

    public void addFilm(@NonNull Film film) {
        if (exists(film)) updateFilm(film);
        else insertNewFilm(film);
    }

    private void insertNewFilm(@NonNull Film film) {
        mDatabase.insert(MySQLiteHelper.TABLE_FILMS, null, filmToValues(film));
        Log.d(TAG, "insertNewFilm " + film.getTitle() + " ID-AUTOINCREMENT"
                + " with description " + film.getDescription());
    }

    private void updateFilm(@NonNull Film film) {
        mDatabase.update(MySQLiteHelper.TABLE_FILMS, filmToValues(film),
                MySQLiteHelper.COLUMN_ID + " = " + film.getId(), null);
        Log.d(TAG, "updateFilm " + film.getTitle() + " ID-" + film.getId()
                + " with description " + film.getDescription());
    }

    public List<Film> getAllFilms(@Nullable String actorFilter) {
        List<Film> films = new ArrayList<>();

        if (actorFilter != null) {
            actorFilter = MySQLiteHelper.COLUMN_PROTAGONIST + " LIKE '%" + actorFilter + "%'";
        }

        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, actorFilter, null, null, null, null);

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
