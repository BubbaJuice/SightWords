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

        int index = (savedInstanceState == null)?
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
        indexBox.setText(""+currentIndex);
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
            modelView.moveRight();
            updateText();
            return true;
        }
        @Override
        public void onLongPress(MotionEvent event) {
            modelView.moveLeft();
            updateText();
        }
    }
}