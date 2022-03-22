package me.bxhuynh.vocabnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
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
    private static final String CREATED_MONTH = "created_Month";
    private static final String CREATED_YEAR = "created_year";

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
                + IS_STUDYING_COL + " INTEGER, "
                + CREATED_MONTH + " INTEGER,"
                + CREATED_YEAR + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public void addNewWord(String word, String soundLike, String meaning, int isStudying) {
        LocalDate date = LocalDate.now();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COL, word);
        values.put(SOUND_LIKE_COL, soundLike);
        values.put(MEANING_COL, meaning);
        values.put(IS_STUDYING_COL, isStudying);
        values.put(CREATED_MONTH, date.getMonthValue());
        values.put(CREATED_YEAR, date.getYear());
        db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<WordModal> readWords(int isStudying) {
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = "";
        if (isStudying == 1) {
            whereClause = " WHERE " + IS_STUDYING_COL +" = 1";
        }
        Cursor cursorWords = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereClause + " ORDER BY " + WORD_COL, null);
        ArrayList<WordModal> wordModalArrayList = new ArrayList<>();

        if (cursorWords.moveToFirst()) {
            do {
                wordModalArrayList.add(new WordModal(
                        cursorWords.getInt(0),
                        cursorWords.getString(1),
                        cursorWords.getString(3),
                        cursorWords.getString(2),
                        Integer.parseInt(cursorWords.getString(4)),
                        cursorWords.getInt(5),
                        cursorWords.getInt(6)
                        ));
            } while(cursorWords.moveToNext());
        }
        cursorWords.close();
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
    }

    public void updateWordById(String id, String word, String soundlike, String meaning, int isStudying ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COL, word);
        values.put(SOUND_LIKE_COL, soundlike);
        values.put(MEANING_COL, meaning);
        values.put(IS_STUDYING_COL, isStudying);
        db.update(TABLE_NAME, values, ID_COL + "=?", new String[]{id} );
    }

    public int getCountWords(boolean studying) {
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = "";
        if (studying) {
            whereClause = " WHERE " + IS_STUDYING_COL +" = 1";
        }
        Cursor cursorWords = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereClause, null);
        int count  = cursorWords.getCount();
        return count;
    }

    public void deleteWord(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, WORD_COL+"=?", new String[]{word});
    }

    public ArrayList<StudiedWordsStatistic> getStatistics() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<StudiedWordsStatistic> arr = new ArrayList<>();
        LocalDate date = LocalDate.now();
        int currentMonth = date.getMonthValue(), currentYear = date.getYear();
        int count = 0;
        while (count < 6) {
            String whereClause = " WHERE " + CREATED_MONTH +" = " + currentMonth + " AND " + CREATED_YEAR + " = " + currentYear;
            Cursor cursorWords = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereClause, null);
            int total = cursorWords.getCount();
            arr.add(new StudiedWordsStatistic(currentMonth, currentYear, total));

            if (currentMonth - 1 > 0) {
                currentMonth = currentMonth - 1;
            } else {
                currentMonth = 12;
                currentYear = currentYear - 1;
            }
            count = count + 1;
        }
        return arr;
    }

}
