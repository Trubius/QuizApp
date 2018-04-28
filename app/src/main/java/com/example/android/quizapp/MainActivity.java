package com.example.android.quizapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String PLAY_TAG = "PLAY_TAG";
    private static final String SUBMIT_TAG = "SUBMIT_TAG";
    MediaPlayer mPlayer;
    private int score = 0;
    private EditText nameField;
    private RadioGroup q1, q4, q6;
    private RadioButton q1_a2;
    private CheckBox q2_a1, q2_a2, q2_a3, q2_a4;
    private CheckBox q3_a1, q3_a2, q3_a3, q3_a4;
    private RadioButton q4_a4;
    private EditText q5_a1;
    private RadioButton q6_a3;
    private Button submitButton;
    private ImageButton playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.name_field);
        submitButton = findViewById(R.id.submit_button);
        playButton = findViewById(R.id.play_button);
        q1 = findViewById(R.id.q1_radiogroup);
        q4 = findViewById(R.id.q4_radiogroup);
        q6 = findViewById(R.id.q6_radiogroup);
        q1_a2 = findViewById(R.id.q1_a2_radiobutton);
        q2_a1 = findViewById(R.id.q2_a1_checkbox);
        q2_a2 = findViewById(R.id.q2_a2_checkbox);
        q2_a3 = findViewById(R.id.q2_a3_checkbox);
        q2_a4 = findViewById(R.id.q2_a4_checkbox);
        q3_a1 = findViewById(R.id.q3_a1_checkbox);
        q3_a2 = findViewById(R.id.q3_a2_checkbox);
        q3_a3 = findViewById(R.id.q3_a3_checkbox);
        q3_a4 = findViewById(R.id.q3_a4_checkbox);
        q4_a4 = findViewById(R.id.q4_a4_radiobutton);
        q5_a1 = findViewById(R.id.q5_a1_text);
        q6_a3 = findViewById(R.id.q6_a3_radiobutton);


        submitButton.setTag(SUBMIT_TAG);
        submitButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        mPlayer = MediaPlayer.create(this, R.raw.answer6);
        setupUI(findViewById(R.id.parent_view));
    }

    @Override
    public void onClick(View v) {
        String status = (String) v.getTag();
        switch (v.getId()) {
            case R.id.play_button:
                if (mPlayer.isPlaying()) {   // Checks music if it's playing
                    mPlayer.pause();
                    playButton.setImageResource(R.drawable.ic_play);
                } else {
                    mPlayer.start();
                    playButton.setImageResource(R.drawable.ic_pause);
                }
                break;
            case R.id.submit_button:
                if (status.equals(SUBMIT_TAG)) {
                    if (checkAnswers()) {
                        displayResult();
                        submitButton.setText(R.string.play_again);
                        v.setTag(PLAY_TAG); // Set the text to play again
                    }
                } else {
                    submitButton.setText(R.string.submit);
                    v.setTag(SUBMIT_TAG); // Set the text to submit
                    reset();
                }
                break;
        }
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Hide soft keyboard outside of the EditText
     */

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    // Checks answers before submit

    private boolean checkAnswers() {
        String name = nameField.getText().toString();

        if (name.equals("")) {
            Toast.makeText(this, "Don't forget to write down your name", Toast.LENGTH_LONG).show();
        } else if (q1.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please answer the 1st question!", Toast.LENGTH_LONG).show();
        } else if (!q2_a1.isChecked() && !q2_a2.isChecked() && !q2_a3.isChecked() && !q2_a4.isChecked()) {
            Toast.makeText(this, "Please answer the 2nd question!", Toast.LENGTH_LONG).show();
        } else if (!q3_a1.isChecked() && !q3_a2.isChecked() && !q3_a3.isChecked() && !q3_a4.isChecked()) {
            Toast.makeText(this, "Please answer the 3rd question!", Toast.LENGTH_LONG).show();
        } else if (q4.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please answer the 4th question!", Toast.LENGTH_LONG).show();
        } else if (q5_a1.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please answer the 5th question!", Toast.LENGTH_LONG).show();
        } else if (q6.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please answer the 6th question!", Toast.LENGTH_LONG).show();
        } else {
            return true;
        }
        return false;
    }

    // Score counter

    private void calculateScore() {
        if (q1_a2.isChecked()) {
            score++;
        }
        if (q2_a1.isChecked()) {
            score++;
        }
        if (q3_a1.isChecked() && q3_a2.isChecked() && q3_a4.isChecked() && !q3_a3.isChecked()) {
            score++;
        }
        if (q4_a4.isChecked()) {
            score++;
        }
        String q5Answer = q5_a1.getText().toString();
        if (q5Answer.length() > 0 && (Integer.parseInt(q5Answer) == 9)) {
            score++;
        }
        if (q6_a3.isChecked()) {
            score++;
        }
    }

    // Display your result in a toast

    private void displayResult() {
        String name = nameField.getText().toString();
        calculateScore();
        if (score == 5) {
            Toast.makeText(this, "You did it, " + name + "! You have " + score + " points out of 5.", Toast.LENGTH_LONG).show();
        } else if (score >= 3) {
            Toast.makeText(this, "Not bad " + name + ", you have " + score + " points out of 5.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You're better than that, " + name + "! You have " + score + " points out of 5.", Toast.LENGTH_LONG).show();
        }
        changeColor();
    }

    // Change the color of the right answers

    private void changeColor() {
        changeTextInputFieldColors(R.color.green);
        q5_a1.setText(getString(R.string.answer5), TextView.BufferType.NORMAL);
    }

    private void reset() {

        score = 0;

        q1.clearCheck();
        q4.clearCheck();
        q6.clearCheck();

        q2_a1.setChecked(false);
        q2_a2.setChecked(false);
        q2_a3.setChecked(false);
        q2_a4.setChecked(false);

        q3_a1.setChecked(false);
        q3_a2.setChecked(false);
        q3_a3.setChecked(false);
        q3_a4.setChecked(false);

        q5_a1.setText("");

        changeTextInputFieldColors(R.color.black);
    }

    private void changeTextInputFieldColors(int colorCode) {
        TextView[] inputs = {q1_a2, q2_a1, q2_a3, q2_a4, q3_a3, q3_a4, q4_a4, q5_a1, q6_a3};
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].setTextColor(getResources().getColor(colorCode));
        }
    }
}
