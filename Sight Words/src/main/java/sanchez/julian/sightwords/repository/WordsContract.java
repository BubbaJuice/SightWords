package sanchez.julian.sightwords.repository;

import android.provider.BaseColumns;

public final class WordsContract implements  BaseColumns {

    private WordsContract() {}

    public static String WORDS_TABLE_NAME = "words";
    public static String WORD_COLUMN_WORD_NAME = "word";
    public static String WORDS_COLUMN_IS_COMPLETE_NAME = "is_complete";

    public static String createWordsTableSql =
            new StringBuilder()
                    .append("CREATE TABLE IF NOT EXISTS ").append(WORDS_TABLE_NAME).append("\n")
                    .append("(\n")
                    .append(_ID).append(" integer primary key autoincrement not null, \n")
                    .append(WORD_COLUMN_WORD_NAME).append(" varchar(255) not null,\n")
                    .append(WORDS_COLUMN_IS_COMPLETE_NAME).append(" boolean not null \n")
                    .append(")").toString();

    public static String dropWordsTableSql =
            new StringBuilder()
                    .append("DROP TABLE IF EXISTS ").append(WORDS_TABLE_NAME).toString();
}
