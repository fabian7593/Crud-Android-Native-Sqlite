package com.example.keybellsoft.crudandroidnativesql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Fabian Rosales Esquivel - Frosquivel Developer on 10/11/2015.
 * Please contact me at www.frosquivel.com
 * Blog fabian7593@gmail.com/blog
 * fabian7593@gmail.com
 * Copyright © 2016 KeyBellSoftCR. All rights reserved.
 */
public class DataBaseHelper {
    private static SQLiteDatabase sampleDB = null;

    /**
     * The constructor
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        createDataBase(context);
    }

    /**
     * This method create the database
     *
     * @param context set the context to create database
     */
    public void createDataBase(Context context) {
        sampleDB = context.openOrCreateDatabase("TestMovieDB.db", Context.MODE_PRIVATE, null);


        //Table Categories
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + "CATEGORY" +
                "( idCategory INTEGER PRIMARY KEY AUTOINCREMENT," +
                " categoryName TEXT," +
                " code INTEGER);");

        //Table Movies
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + "MOVIE" +
                " (idMovie INTEGER PRIMARY KEY AUTOINCREMENT," +
                " movieName TEXT," +
                " quantity INTEGER," +
                " idMovieCategory INTEGER," +
                " FOREIGN KEY(idMovieCategory) REFERENCES CATEGORY(idCategory));");
    }

    /**
     * This method execute an sql query
     *
     * @param sqlQuery set the query
     */
    public void executeSql(String sqlQuery) {
        sampleDB.execSQL(sqlQuery);
    }

    /**
     * This method execute a consult, query and convert to cursor
     *
     * @param sqlQuery set the query
     * @return Cursor
     */
    public Cursor rawQuery(String sqlQuery) {
        return sampleDB.rawQuery(sqlQuery, null);
    }
}
