package sanchez.julian.sightwords.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import sanchez.julian.sightwords.Word;


public class WordSqliteRepository implements WordRepositoryApi {

    private WordSqlHelper dbHelper;

    public WordSqliteRepository(WordSqlHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Word getWord(int index) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String table = WordsContract.WORDS_TABLE_NAME;

        String[] fields =
                new String[] {
                        WordsContract.WORD_COLUMN_WORD_NAME,
                        WordsContract.WORDS_COLUMN_IS_COMPLETE_NAME
        };
        String selection = WordsContract._ID + " = ? ";
        String[] selectionArgs = new String[]{ String.valueOf(index)};

        Cursor cursor = db.query(table,
                fields,
                selection,
                selectionArgs,
                null, null, null, null);

        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                return null;
            }
            String wordValue = cursor.getString(cursor.getColumnIndexOrThrow(
                    WordsContract.WORD_COLUMN_WORD_NAME
                    ));
            boolean isComplete =
                     cursor.getInt(cursor.getColumnIndexOrThrow(
                            WordsContract.WORDS_COLUMN_IS_COMPLETE_NAME
                    )) == 1;
            Word word = new Word(wordValue , isComplete);
            cursor.close();
            return word;
        }
        cursor.close();
        return null;
    }

    @Override
    public List<Word> getWords() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String table = WordsContract.WORDS_TABLE_NAME;

        String[] fields =
                new String[] {
                        WordsContract.WORD_COLUMN_WORD_NAME,
                        WordsContract.WORDS_COLUMN_IS_COMPLETE_NAME
                };
        String selection = null;

        Cursor cursor = db.query(table,
                fields,
                selection,
                null, null, null, null);

        List words = new ArrayList<>();
        while(cursor.moveToNext()) {
            String wordValue = cursor.getString(cursor.getColumnIndexOrThrow(
                    WordsContract.WORD_COLUMN_WORD_NAME
            ));
            boolean isComplete =
                    cursor.getInt(cursor.getColumnIndexOrThrow(
                            WordsContract.WORDS_COLUMN_IS_COMPLETE_NAME
                    )) == 1;
            Word word = new Word(wordValue , isComplete);
            words.add(word);
        }
        cursor.close();
        return words;
    }

    @Override
    public Word updateWord(int index, Word word) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordsContract.WORD_COLUMN_WORD_NAME, word.getWord());
        values.put(WordsContract.WORDS_COLUMN_IS_COMPLETE_NAME, word.isComplete());

        String selection = WordsContract._ID + " = ? ";
        String[] selectionValue = new String[] { String.valueOf(index) };

        int result = db.update(WordsContract.WORDS_TABLE_NAME,
                values,
                selection,
                selectionValue
        );

        return word;
    }

    @Override
    public int putWord(Word word) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        System.out.println(" ===> " + word.getWord() + " " + word.isComplete());
        ContentValues values = new ContentValues();
        values.put(WordsContract.WORD_COLUMN_WORD_NAME, word.getWord());
        values.put(WordsContract.WORDS_COLUMN_IS_COMPLETE_NAME, word.isComplete() ? 1 : 0);

         long result = db.insertOrThrow(
                WordsContract.WORDS_TABLE_NAME,
                null,
                values
        );

        System.out.println(" ===> " + result);
        return (int) result;
    }

    @Override
    public void putWords(List<Word> words) {
        for (int i = 0; i < words.size(); i++) {
            this.putWord(words.get(i));
        }
    }

    @Override
    public void updateWords(List<Word> words) {
        for (int i = 0; i < words.size(); i++) {
            this.updateWord(i, words.get(i));
        }
    }
}
