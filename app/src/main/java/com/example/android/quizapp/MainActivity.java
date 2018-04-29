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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String SUBMIT_TEXT = "Submit";
    private static MediaPlayer mPlayer;
    private static int score = 0;
    private EditText nameField;
    private RadioGroup q1, q4, q6;
    private RadioButton q1_a2, q4_a4, q6_a3;
    private CheckBox q2_a1, q2_a2, q2_a3, q2_a4, q3_a1, q3_a2, q3_a3, q3_a4;
    private EditText q5_a1;
    private Button submitButton;
    private ImageButton playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.name_field);
        submitButton = findViewById(R.id.submit_button);
        playButton = findViewById(R.id.play_button);
        q1 = findViewById(R.id.q1);
        q4 = findViewById(R.id.q4);
        q6 = findViewById(R.id.q6);
        q1_a2 = findViewById(R.id.q1_a2);
        q2_a1 = findViewById(R.id.q2_a1);
        q2_a2 = findViewById(R.id.q2_a2);
        q2_a3 = findViewById(R.id.q2_a3);
        q2_a4 = findViewById(R.id.q2_a4);
        q3_a1 = findViewById(R.id.q3_a1);
        q3_a2 = findViewById(R.id.q3_a2);
        q3_a3 = findViewById(R.id.q3_a3);
        q3_a4 = findViewById(R.id.q3_a4);
        q4_a4 = findViewById(R.id.q4_a4);
        q5_a1 = findViewById(R.id.q5_a1);
        q6_a3 = findViewById(R.id.q6_a3);

        submitButton.setOnClickListener(new ButtonClick());
        playButton.setOnClickListener(new ButtonClick());
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.answer6);
        }
        initFocus();
        setupUI(findViewById(R.id.root_view));
    }

    private void requestFocus() {
        View parentView = (findViewById(R.id.root_view));
        parentView.requestFocus();
    }

    private void initFocus() {
        View parentView = (findViewById(R.id.root_view));
        parentView.setFocusableInTouchMode(true);
        requestFocus();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current score state
        savedInstanceState.putString(SUBMIT_TEXT, submitButton.getText().toString());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        submitButton.setText(savedInstanceState.getString(SUBMIT_TEXT));
        setPlayPauseImageResource();
        if (submitButton.getText().toString().equals("Play again")) {
            displayResult();
        }
        requestFocus();
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

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Checks answers before submit
     */

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


    /**
     * Display your result in a toast
     */

    private void displayResult() {
        String name = nameField.getText().toString();
        score = calculateScore();
        if (score == 12) {
            Toast.makeText(this, "You did it, " + name + "! You have " + score + " points out of 12.", Toast.LENGTH_LONG).show();
        } else if (score >= 6) {
            Toast.makeText(this, "Not bad " + name + ", you have " + score + " points out of 12.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You're better than that, " + name + "! You have " + score + " points out of 12.", Toast.LENGTH_LONG).show();
        }
        changeColor();
        setAnswerButtonsDisabled(false);
    }

    /**
     * Score counter
     */

    private int calculateScore() {
        int score = 0;
        CompoundButton[] rightAnswers = {q1_a2, q2_a1, q2_a3, q2_a4, q3_a3, q3_a4, q4_a4, q6_a3};
        CompoundButton[] wrongAnswers = {q2_a2, q3_a1, q3_a2};
        for (int i = 0; i < rightAnswers.length; i++) {
            if (rightAnswers[i].isChecked()) {
                score++;
            }
        }
        for (int j = 0; j < wrongAnswers.length; j++) {
            if (!wrongAnswers[j].isChecked()) {
                score++;
            }
        }
        String q5Answer = q5_a1.getText().toString();
        if (q5Answer.length() > 0 && (Integer.parseInt(q5Answer) == 9)) {
            score++;
        }
        return score;
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
        setAnswerButtonsDisabled(true);
    }

    /**
     * Change the color of the right answers
     */

    private void changeColor() {
        changeTextInputFieldColors(R.color.green);
        q5_a1.setText(getString(R.string.answer5), TextView.BufferType.NORMAL);
    }

    private void changeTextInputFieldColors(int colorCode) {
        TextView[] inputs = {q1_a2, q2_a1, q2_a3, q2_a4, q3_a3, q3_a4, q4_a4, q5_a1, q6_a3};
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].setTextColor(getResources().getColor(colorCode));
        }
    }

    private void setAnswerButtonsDisabled(boolean enabled) {
        CheckBox[] checkBoxes = {q2_a1, q2_a2, q2_a3, q2_a4, q3_a1, q3_a2, q3_a3, q3_a4,};
        RadioGroup[] radioGroups = {q1, q4, q6};
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setEnabled(enabled);
        }
        for (int j = 0; j < radioGroups.length; j++) {
            for (int k = 0; k < radioGroups[j].getChildCount(); k++) {
                radioGroups[j].getChildAt(k).setEnabled(enabled);
            }
        }
        nameField.setEnabled(enabled);
        q5_a1.setEnabled(enabled);
    }

    public void submitButtonClicked() {
        if (submitButton.getText().toString().equals("Submit")) {
            if (checkAnswers()) {
                displayResult();
                submitButton.setText(R.string.play_again);
            }
        } else {
            submitButton.setText(R.string.submit);
            reset();
        }
    }

    public void playButtonClicked() {
        if (mPlayer.isPlaying()) {   // Checks music if it's playing
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        setPlayPauseImageResource();
    }

    public void setPlayPauseImageResource() {
        if (mPlayer.isPlaying()) {   // Checks music if it's playing
            playButton.setImageResource(R.drawable.ic_pause);
        } else {
            playButton.setImageResource(R.drawable.ic_play);
        }
    }

    class ButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.play_button:
                    playButtonClicked();
                    break;
                case R.id.submit_button:
                    submitButtonClicked();
                    break;
            }
        }
    }
}
