package sanchez.julian.sightwords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> wordList = Arrays.asList("the", "of", "and", "a", "to", "in", "is", "you", "that", "it", "he", "was", "for", "on", "are", "as", "with", "his", "they", "I", "at", "be", "this", "have", "from", "or", "one", "had", "by", "words", "but", "not", "what", "all", "were", "were", "when", "your", "can", "said", "there", "use", "un", "each", "which", "she", "do", "how", "their", "if", "will", "up", "other", "about", "out", "many", "then", "them", "these", "so", "some", "her", "would", "make", "like", "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", "number", "no", "way", "could", "people", "my", "than", "first", "water", "been", "called", "who", "oil", "sit", "now", "find", "long", "down", "day", "did", "get", "come", "made", "may", "part");
    private List<Word> words;
    private int index = 0;
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final CheckBox checkBox = (findViewById(R.id.checkbox));

        if (savedInstanceState == null) {

            // create the words
            words = new ArrayList<Word>();
            for (int i = 0; i < wordList.size(); i++) {
                Word w = new Word(wordList.get(i), false);
                words.add(w);
            }
        } else {
            Serializable savedWords = savedInstanceState.getSerializable("words");
            words = (ArrayList<Word>) savedWords;
            index = savedInstanceState.getInt("index");
        }

        updateText(index);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateCheckbox(index, isChecked);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("words", (Serializable) words);
        outState.putInt("index", index);
    }

    private void updateCheckbox(int index, boolean isChecked) {
        final CheckBox checkBox = (findViewById(R.id.checkbox));
        Word currentWord = words.get(index);
        currentWord.setComplete(isChecked);
    }

    private void updateText(int index) {
        final TextView wordBox = (findViewById(R.id.word));
        final TextView indexBox = (findViewById(R.id.index));
        final CheckBox checkBox = (findViewById(R.id.checkbox));
        Word currentWord = words.get(index);
        wordBox.setText(currentWord.getWord());
        indexBox.setText(""+index);
        checkBox.setChecked(currentWord.isComplete());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent event) {
            index++;
            if (index >= words.size())
                index = 0;
            updateText(index);
            return true;
        }
        @Override
        public void onLongPress(MotionEvent event) {
            index--;
            if (index < 0)
                index = words.size() - 1;
            updateText(index);
        }
    }
}