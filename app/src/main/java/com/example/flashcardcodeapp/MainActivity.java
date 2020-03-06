package com.example.flashcardcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }


        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 100);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {

            if (requestCode == 100 && resultCode == RESULT_OK) {

                //this 100 needs to match the original 100 set at startActivityForResult
                String newQuestion = data.getExtras().getString("FCQuestion"); //
                String newAnswer = data.getExtras().getString("FCAnswer"); //

                TextView currentFCQuestion = findViewById(R.id.flashcard_question);
                currentFCQuestion.setText(newQuestion);

                TextView currentFCAnswer = findViewById(R.id.flashcard_answer);
                currentFCAnswer.setText(newAnswer);
            }

        }
    }

}
