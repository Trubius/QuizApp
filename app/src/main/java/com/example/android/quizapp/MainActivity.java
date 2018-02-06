package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    CheckBox q3_a1, q3_a2, q3_a3, q3_a4;
    RadioButton q1_a2, q2_a4, q4_a4;
    EditText q5_a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    private void submitAnswer(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        if(name.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Don't forget to write down your name", Toast.LENGTH_LONG).show();
        }
    }
}
