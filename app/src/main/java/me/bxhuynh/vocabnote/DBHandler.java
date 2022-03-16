package me.bxhuynh.vocabnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
//        db.close();
    }
}
