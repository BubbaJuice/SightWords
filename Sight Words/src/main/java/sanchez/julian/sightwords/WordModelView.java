package sanchez.julian.sightwords;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sanchez.julian.sightwords.repository.WordSqlHelper;
import sanchez.julian.sightwords.repository.WordSqliteRepository;

public class WordModelView {
    private Context context;
    WordSqliteRepository repo;
    Word currentWord;
    int index;
    int wordsSize;

    public  WordModelView(Context context, int index) {
        this.context = context;
        WordSqlHelper  dbHelper = new WordSqlHelper (context);
        repo = new WordSqliteRepository(dbHelper);

        wordsSize = repo.getWords().size();
        if (wordsSize  == 0) {
            // create the words
            List<String> wordList = Arrays.asList("the", "of", "and", "a", "to", "in", "is", "you", "that", "it", "he", "was", "for", "on", "are", "as", "with", "his", "they", "I", "at", "be", "this", "have", "from", "or", "one", "had", "by", "words", "but", "not", "what", "all", "were", "were", "when", "your", "can", "said", "there", "use", "un", "each", "which", "she", "do", "how", "their", "if", "will", "up", "other", "about", "out", "many", "then", "them", "these", "so", "some", "her", "would", "make", "like", "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", "number", "no", "way", "could", "people", "my", "than", "first", "water", "been", "called", "who", "oil", "sit", "now", "find", "long", "down", "day", "did", "get", "come", "made", "may", "part");
            List<Word> words;
            words = new ArrayList<Word>();
            for (int i = 0; i < wordList.size(); i++) {
                Word w = new Word(wordList.get(i), false);
                words.add(w);
            }
            repo.putWords(words);
        }
        wordsSize = repo.getWords().size();
        this.index = index;
        currentWord = repo.getWord(index);
    }

    public WordModelView moveRight() {
        index++;
        if (index > wordsSize)
            index = 1;
        currentWord = repo.getWord(index);
        return this;
    }

    public WordModelView moveLeft() {
        index--;
        if (index <= 0)
            index = wordsSize;
        currentWord = repo.getWord(index);
        return this;
    }

    public WordModelView setWordComplete(boolean  isComplete) {
        currentWord.setComplete(isComplete);
        repo.updateWord(index, currentWord);
        return this;
    }

    public Word getCurrentWord() {
       return currentWord;
    }
    public int getIndex() {
       return index;
    }
}
