package com.and119_idi.myfilmdatabase.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
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

    public void checkVersion(SQLiteDatabase database) {
        int version = database.getVersion();
        if (version < DATABASE_VERSION) onUpgrade(database, version, DATABASE_VERSION);
        else if (version > DATABASE_VERSION) onDowngrade(database, version, DATABASE_VERSION);
    }
}
