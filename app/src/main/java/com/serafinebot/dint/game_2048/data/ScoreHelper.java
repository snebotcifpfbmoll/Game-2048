package com.serafinebot.dint.game_2048.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ScoreHelper extends SQLiteOpenHelper {
    private static final String TAG = "UserHelper";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "game-2048";
    private static final String TABLE = "score";
    private static final String ID_COL = "id";
    private static final String SCORE_COL = "score";
    private static final String PLAYER_COL = "player";

    private SQLiteDatabase readableDB;
    private SQLiteDatabase writableDB;

    public ScoreHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE +
                " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SCORE_COL + " INTEGER, " +
                PLAYER_COL + " TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public List<Score> getAll() {
        Cursor cursor = null;
        List<Score> scores = new ArrayList<>();
        try {
            if (this.readableDB == null)
                this.readableDB = getReadableDatabase();
            cursor = this.readableDB.rawQuery("SELECT * FROM " + TABLE + ";", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Score score = new Score();
                    score.id = cursor.getLong(cursor.getColumnIndex("id"));
                    score.score = cursor.getInt(cursor.getColumnIndex(SCORE_COL));
                    score.player = cursor.getString(cursor.getColumnIndex(PLAYER_COL));
                    scores.add(score);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getAll: ", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return scores;
    }

    public Score get(long id) {
        Cursor cursor = null;
        Score score = new Score();
        try {
            if (this.readableDB == null)
                this.readableDB = getReadableDatabase();
            cursor = this.readableDB.rawQuery("SELECT * FROM " + TABLE +
                    " WHERE id = " + id + ";", null);
            if (cursor != null && cursor.moveToFirst()) {
                score.id = cursor.getLong(cursor.getColumnIndex(ID_COL));
                score.score = cursor.getInt(cursor.getColumnIndex(SCORE_COL));
                score.player = cursor.getString(cursor.getColumnIndex(PLAYER_COL));
            }
        } catch (Exception e) {
            Log.e(TAG, "get: ", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return score;
    }

    public long add(Score score) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(SCORE_COL, score.score);
        values.put(PLAYER_COL, score.player);
        try {
            if (this.writableDB == null)
                this.writableDB = getWritableDatabase();
            newId = this.writableDB.insert(TABLE, null, values);
        } catch (Exception e) {
            Log.e(TAG, "add: ", e);
        }
        return newId;
    }

    public boolean delete(long id) {
        int deleted = 0;
        try {
            if (this.writableDB == null)
                this.writableDB = getWritableDatabase();
            deleted = this.writableDB.delete(TABLE, "id = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e(TAG, "delete: ", e);
        }
        return deleted > 0;
    }

    public boolean delete(Score score) {
        return delete(score.id);
    }

    public boolean update(long id, Score newScore) {
        int rows = 0;
        ContentValues values = new ContentValues();
        values.put(SCORE_COL, newScore.score);
        values.put(PLAYER_COL, newScore.player);
        try {
            if (this.writableDB == null)
                this.writableDB = getWritableDatabase();
            rows = this.writableDB.update(TABLE, values, "id = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e(TAG, "update: ", e);
        }
        return rows > 0;
    }
}
