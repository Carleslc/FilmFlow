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
        mDatabaseHelper = new MySQLiteHelper(context, this);
    }

    public void open() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mDatabaseHelper.checkVersion(mDatabase);
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public void addDefaultFilms() {
        addFilm(new Film("The Shawshank Redemption", "Frank Darabont", "Canada", 1994, "Tim Robbins",
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", 9));
        addFilm(new Film("The Godfather", "Francis Ford Coppola", "United States", 1972, "Marlon Brando",
                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", 9));
        addFilm(new Film("Apocalypse Now", "Francis Ford Coppola", "United States", 1979, "Marlon Brando",
                "During the Vietnam War, Captain Willard is sent on a dangerous mission into Cambodia to assassinate a renegade colonel who has set himself up as a god among a local tribe.", 8));
        addFilm(new Film("Star Wars", "George Lucas", "United States", 1977, "Marlon Brando",
                "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a wookiee and two droids to save the galaxy from the Empire's world-destroying battle-station, while also attempting to rescue Princess Leia from the evil Darth Vader.", 9));
        addFilm(new Film("Pan's Labyrinth", "Guillermo del Toro", "Spain", 2006, "Ariadna Gil",
                "In the falangist Spain of 1944, the bookish young stepdaughter of a sadistic army officer escapes into an eerie but captivating fantasy world.", 8));
        addFilm(new Film("The Matrix", "The Wachowski Brothers", "United States", 1999, "Keanu Reeves",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", 9));
        addFilm(new Film("Forrest Gump", "Robert Zemeckis", "United States", 1994, "Tom Hanks",
                "Forrest Gump, while not intelligent, has accidentally been present at many historic moments, but his true love, Jenny Curran, eludes him.", 9));
        addFilm(new Film("The Lord of the Rings: The Return of the King", "Peter Jackson", "New Zealand", 2003, "Elijah Wood",
                "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", 9));
        addFilm(new Film("Pulp Fiction", "Quentin Tarantino", "United States", 1994, "John Travolta",
                "The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", 9));
        addFilm(new Film("Sen to Chihiro no kamikakushi", "Hayao Miyazaki", "Japan", 2001, "Daveigh Chase",
                "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.", 9));
        addFilm(new Film("The Dark Knight", "Christopher Nolan", "United Kingdom", 2008, "Christian Bale",
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.", 9));
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
