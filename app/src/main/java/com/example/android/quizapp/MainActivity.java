package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    EditText nameField;
    RadioGroup q1, q2, q4;
    RadioButton q1_a1, q1_a2, q1_a3, q1_a4;
    RadioButton q2_a1, q2_a2, q2_a3, q2_a4;
    CheckBox q3_a1, q3_a2, q3_a3, q3_a4;
    RadioButton q4_a1, q4_a2, q4_a3, q4_a4;
    EditText q5_a1;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = (EditText) findViewById(R.id.name_field);
        submitButton = (Button) findViewById(R.id.submit_button);
        q1 = (RadioGroup) findViewById(R.id.q1_radiogroup);
        q2 = (RadioGroup) findViewById(R.id.q2_radiogroup);
        q4 = (RadioGroup) findViewById(R.id.q4_radiogroup);
        q1_a1 = (RadioButton) findViewById(R.id.q1_a1_radiobutton);
        q1_a2 = (RadioButton) findViewById(R.id.q1_a2_radiobutton); //correct answer
        q1_a3 = (RadioButton) findViewById(R.id.q1_a3_radiobutton);
        q1_a4 = (RadioButton) findViewById(R.id.q1_a4_radiobutton);
        q2_a1 = (RadioButton) findViewById(R.id.q2_a1_radiobutton); //correct answer
        q2_a2 = (RadioButton) findViewById(R.id.q2_a2_radiobutton);
        q2_a3 = (RadioButton) findViewById(R.id.q2_a3_radiobutton);
        q2_a4 = (RadioButton) findViewById(R.id.q2_a4_radiobutton);
        q3_a1 = (CheckBox) findViewById(R.id.q3_a1_checkbox); //correct answer
        q3_a2 = (CheckBox) findViewById(R.id.q3_a2_checkbox); //correct answer
        q3_a3 = (CheckBox) findViewById(R.id.q3_a3_checkbox);
        q3_a4 = (CheckBox) findViewById(R.id.q3_a4_checkbox); //correct answer
        q4_a1 = (RadioButton) findViewById(R.id.q4_a1_radiobutton);
        q4_a2 = (RadioButton) findViewById(R.id.q4_a2_radiobutton);
        q4_a3 = (RadioButton) findViewById(R.id.q4_a3_radiobutton);
        q4_a4 = (RadioButton) findViewById(R.id.q4_a4_radiobutton); //correct answer
        q5_a1 = (EditText) findViewById(R.id.q5_a1_text); //correct answer is 21
    }

    private int getScore() {
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
        if (Integer.parseInt(q5_a1.getText().toString()) == 21) {
            score++;
        }
        return score;
    }

    public void submitAnswer(View view) {
        String name = nameField.getText().toString();

        if (name.equalsIgnoreCase("")) {
            Toast.makeText(this, "Don't forget to write down your name", Toast.LENGTH_LONG).show();
            return;
        }
        if (q1.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please answer the 1st question!", Toast.LENGTH_LONG).show();
            return;
        }
        if (q2.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please answer the 2nd question!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!q3_a1.isChecked() && !q3_a2.isChecked() && !q3_a4.isChecked() && !q3_a3.isChecked()) {
            Toast.makeText(this, "Please answer the 3rd question!", Toast.LENGTH_LONG).show();
            return;
        }
        if (q4.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please answer the 4th question!", Toast.LENGTH_LONG).show();
            return;
        }
        if (q5_a1.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please answer the 5th question!", Toast.LENGTH_LONG).show();
            return;
        }
        getScore();
        if (score == 5) {
            Toast.makeText(this, "You did it, " + name + "! You are awesome!", Toast.LENGTH_LONG).show();
        } else if (score >= 3) {
            Toast.makeText(this, "Not bad " + name + ", you have " + score + " points out of 5.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You're better than that, " + name + "! You have " + score + " points out of 5.", Toast.LENGTH_LONG).show();
        }
        //submitButton.setEnabled(false);
        reset();
    }

    private void reset() {

        score = 0;

        q1.clearCheck();
        q2.clearCheck();
        q4.clearCheck();

        q3_a1.setChecked(false);
        q3_a2.setChecked(false);
        q3_a3.setChecked(false);
        q3_a4.setChecked(false);

        q5_a1.setText("");
    }
}
