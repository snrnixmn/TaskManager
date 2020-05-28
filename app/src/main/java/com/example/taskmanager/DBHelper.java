package com.example.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //TODO Define the Database properties
    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASK = "Task";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "date";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO CREATE TABLE Note
        String createTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createTableSql);

        Log.i("info", "created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK );
        onCreate(db);
    }

    public void insertToDo(String date, String data) {
        //TODO insert the data into the database

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_NAME, date);
        // Store the column name as key and the date as value
        values.put(COLUMN_DESCRIPTION, data);
        // Insert the row into the TABLE_TASK
        db.insert(TABLE_TASK, null, values);
        // Close the database connection
        db.close();
    }

    public ArrayList<Task> getTask() {
        //TODO return records in Java objects

        ArrayList<Task> notes = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                Task obj = new Task(id, name, description);
                notes.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

}