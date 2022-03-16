package me.bxhuynh.vocabnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "vocabDB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "mywords";
    private static final String ID_COL = "id";
    private static final String WORD_COL = "word";
    private static final String MEANING_COL = "meaning";
    private static final String SOUND_LIKE_COL = "soundlike";
    private static final String IS_STUDYING_COL = "is_studying"; // int: 1 is true, 0 is false

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORD_COL + " TEXT, "
                + MEANING_COL + " TEXT, "
                + SOUND_LIKE_COL + " TEXT, "
                + IS_STUDYING_COL + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public void addNewWord(String word, String soundlike, String meaning, int isStudying) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COL, word);
        values.put(SOUND_LIKE_COL, soundlike);
        values.put(MEANING_COL, meaning);
        values.put(IS_STUDYING_COL, isStudying);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<WordModal> readWords(int isStudying) {
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = "";
        if (isStudying == 1) {
            whereClause = " WHERE " + IS_STUDYING_COL +" = 1";
        }
        Cursor cursorWords = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereClause, null);
        ArrayList<WordModal> wordModalArrayList = new ArrayList<>();

        if (cursorWords.moveToFirst()) {
            do {
                wordModalArrayList.add(new WordModal(cursorWords.getString(1), cursorWords.getString(3), cursorWords.getString(2), Integer.parseInt(cursorWords.getString(4))));
            } while(cursorWords.moveToNext());
        }
        cursorWords.close();
        db.close();
        return wordModalArrayList;
    }

    public void updateWord(String originalWord, String word, String soundlike, String meaning, int isStudying ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COL, word);
        values.put(SOUND_LIKE_COL, soundlike);
        values.put(MEANING_COL, meaning);
        values.put(IS_STUDYING_COL, isStudying);
        db.update(TABLE_NAME, values, WORD_COL + "=?", new String[]{originalWord} );
        db.close();
    }

    public int getCountWords(boolean studying) {
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = "";
        if (studying) {
            whereClause = " WHERE " + IS_STUDYING_COL +" = 1";
        }
        Cursor cursorWords = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereClause, null);
        int count  = cursorWords.getCount();
        db.close();
        return count;
    }

    public void deleteWord(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, WORD_COL+"=?", new String[]{word});
        db.close();
    }
}
