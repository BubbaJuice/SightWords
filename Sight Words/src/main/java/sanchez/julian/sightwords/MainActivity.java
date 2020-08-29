package sanchez.julian.sightwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> words = new ArrayList<>(
            Arrays.asList("the", "of", "and", "a", "to", "in", "is", "you", "that", "it", "he", "was", "for", "on", "are", "as", "with", "his", "they", "I", "at", "be", "this", "have", "from", "or", "one", "had", "by", "words", "but", "not", "what", "all", "were", "were", "when", "your", "can", "said", "there", "use", "un", "each", "which", "she", "do", "how", "their", "if", "will", "up", "other", "about", "out", "many", "then", "them", "these", "so", "some", "her", "would", "make", "like", "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", "number", "no", "way", "could", "people", "my", "than", "first", "water", "been", "called", "who", "oil", "sit", "now", "find", "long", "down", "day", "did", "get", "come", "made", "may", "part")
    );
    private int index = 0;
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView wordBox = (findViewById(R.id.word));
        final TextView indexBox = (findViewById(R.id.index));
        wordBox.setText(words.get(index));
        indexBox.setText(""+index);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        final TextView wordBox = (findViewById(R.id.word));
        final TextView indexBox = (findViewById(R.id.index));
        @Override
        public boolean onDoubleTap(MotionEvent event) {
            index++;
            if (index >= words.size())
                index = 0;
            indexBox.setText(""+index);
            wordBox.setText(words.get(index));
            return true;
        }
        @Override
        public void onLongPress(MotionEvent event) {
            index--;
            if (index < 0)
                index = words.size() - 1;
            wordBox.setText(words.get(index));
            indexBox.setText(""+index);
        }
    }
}