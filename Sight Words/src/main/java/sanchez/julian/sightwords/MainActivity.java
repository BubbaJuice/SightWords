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

import sanchez.julian.sightwords.repository.WordSqlHelper;

public class MainActivity extends AppCompatActivity {

    WordModelView modelView;
    private int index;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox checkBox = (findViewById(R.id.checkbox));

        int index = (savedInstanceState == null) ?
                1 :
                savedInstanceState.getInt("index");

        modelView = new WordModelView(this, index);
        updateText();
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                modelView.setWordComplete(isChecked);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", modelView.getIndex());
    }

    private void updateText() {

        final TextView wordBox = (findViewById(R.id.word));
        final TextView indexBox = (findViewById(R.id.index));
        final CheckBox checkBox = (findViewById(R.id.checkbox));
        Word currentWord = modelView.getCurrentWord();
        int currentIndex = modelView.getIndex();
        wordBox.setText(currentWord.getWord());
        indexBox.setText("" + currentIndex);
        checkBox.setChecked(currentWord.isComplete());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        public void onSwipeRight() {
            modelView.moveLeft();
            updateText();
        }

        public void onSwipeLeft() {
            modelView.moveRight();
            updateText();
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}