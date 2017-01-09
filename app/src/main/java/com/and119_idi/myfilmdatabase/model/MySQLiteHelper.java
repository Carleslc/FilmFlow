package com.and119_idi.myfilmdatabase.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by pr_idi on 10/11/16.
 */
class MySQLiteHelper extends SQLiteOpenHelper {

    static final String TABLE_FILMS = "films";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_COUNTRY = "country";
    static final String COLUMN_YEAR_RELEASE = "year_release";
    static final String COLUMN_DIRECTOR = "director";
    static final String COLUMN_PROTAGONIST = "protagonist";
    static final String COLUMN_CRITICS_RATE = "critics_rate";
    static final String COLUMN_DESCRIPTION = "description";

    private static final String TAG = MySQLiteHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 2;
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_FILMS + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_YEAR_RELEASE + " integer not null, "
            + COLUMN_DIRECTOR + " text not null, "
            + COLUMN_PROTAGONIST + " text not null, "
            + COLUMN_CRITICS_RATE + " integer, "
            + COLUMN_DESCRIPTION + " text"
            + ");";

    MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static void insertNewFilm(SQLiteDatabase database, @NonNull Film film) {
        database.insert(MySQLiteHelper.TABLE_FILMS, null, filmToValues(film));
        Log.d(TAG, "insertNewFilm " + film.getTitle() + " ID-AUTOINCREMENT"
                + " with description " + film.getDescription());
    }

    static ContentValues filmToValues(@NonNull Film film) {
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

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        addDefaultFilms(database);
    }

    private void addDefaultFilms(SQLiteDatabase database) {
        insertNewFilm(database, new Film("The Dark Knight", "Christopher Nolan", "United Kingdom", 2008, "Christian Bale",
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.", 9));
        insertNewFilm(database, new Film("The Godfather", "Francis Ford Coppola", "United States", 1972, "Marlon Brando",
                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", 9));
        insertNewFilm(database, new Film("Apocalypse Now", "Francis Ford Coppola", "United States", 1979, "Marlon Brando",
                "During the Vietnam War, Captain Willard is sent on a dangerous mission into Cambodia to assassinate a renegade colonel who has set himself up as a god among a local tribe.", 8));
        insertNewFilm(database, new Film("Star Wars", "George Lucas", "United States", 1977, "Mark Hamill",
                "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a wookiee and two droids to save the galaxy from the Empire's world-destroying battle-station, while also attempting to rescue Princess Leia from the evil Darth Vader.", 9));
        // Más películas añadidas en un inicio. Comentadas para dejar 4 películas según indica el enunciado de la práctica.
        /*insertNewFilm(database, new Film("The Shawshank Redemption", "Frank Darabont", "Canada", 1994, "Tim Robbins",
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", 9));
        insertNewFilm(database, new Film("Pan's Labyrinth", "Guillermo del Toro", "Spain", 2006, "Ariadna Gil",
                "In the falangist Spain of 1944, the bookish young stepdaughter of a sadistic army officer escapes into an eerie but captivating fantasy world.", 8));
        insertNewFilm(database, new Film("The Matrix", "The Wachowski Brothers", "United States", 1999, "Keanu Reeves",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", 9));
        insertNewFilm(database, new Film("Forrest Gump", "Robert Zemeckis", "United States", 1994, "Tom Hanks",
                "Forrest Gump, while not intelligent, has accidentally been present at many historic moments, but his true love, Jenny Curran, eludes him.", 9));
        insertNewFilm(database, new Film("The Lord of the Rings: The Return of the King", "Peter Jackson", "New Zealand", 2003, "Elijah Wood",
                "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", 9));
        insertNewFilm(database, new Film("Pulp Fiction", "Quentin Tarantino", "United States", 1994, "John Travolta",
                "The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", 9));
        insertNewFilm(database, new Film("Sen to Chihiro no kamikakushi", "Hayao Miyazaki", "Japan", 2001, "Daveigh Chase",
                "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.", 9));*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Updating database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);
        onCreate(db);
    }

    void checkVersion(SQLiteDatabase database) {
        int version = database.getVersion();
        if (version < DATABASE_VERSION) onUpgrade(database, version, DATABASE_VERSION);
        else if (version > DATABASE_VERSION) onDowngrade(database, version, DATABASE_VERSION);
    }
}
