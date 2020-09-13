package sanchez.julian.sightwords.repository;

import java.util.List;

import sanchez.julian.sightwords.Word;

public interface WordRepositoryApi {

    public int putWord(Word word);

    public Word getWord(int index);

    public List<Word> getWords();

    public Word updateWord(int index, Word word);

    public void putWords(List<Word> words);

    public void updateWords(List<Word> words);

}
