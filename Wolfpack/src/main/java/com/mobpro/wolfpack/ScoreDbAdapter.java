package com.mobpro.wolfpack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rachel on 9/18/13.
 */
public class ScoreDbAdapter {

    private static final String DATABASE_NAME = "wolfpack.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "wolfpack";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_POINTS = "points";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private final Context context;
    private ScoreDbHelper dbHelper;
    private SQLiteDatabase db;

    public ScoreDbAdapter(Context context) {
        this.context = context;
    }

    public ScoreDbAdapter open(){
        dbHelper = new ScoreDbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public Score createScore(Integer score, Integer timestamp) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_POINTS, score);
        values.put(COLUMN_TIMESTAMP, timestamp);
        long id = db.insert(TABLE_NAME, null, values);

        return new Score(id, score, timestamp);
    }


    public boolean deleteScore(Score score) {
        return db.delete(TABLE_NAME, COLUMN_ID + "=" + score.getId(), null) > 0;
    }

    public Score getScore(long id){
        Cursor cursor = db.query(true, TABLE_NAME, new String[] {COLUMN_ID, COLUMN_POINTS, COLUMN_TIMESTAMP}, COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return scoreFromCursor(cursor);

    }

    public Cursor getAllScores(){
        return db.query(true, TABLE_NAME, new String[] {COLUMN_ID, COLUMN_POINTS, COLUMN_TIMESTAMP}, null, null, null, null, null, null);
    }

    public static Score scoreFromCursor(Cursor cursor){
        return new Score(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_POINTS)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
    }


    private class ScoreDbHelper extends SQLiteOpenHelper {

        private static final String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_POINTS + " INTEGER, " + COLUMN_TIMESTAMP + " INTEGER)";

        public ScoreDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
