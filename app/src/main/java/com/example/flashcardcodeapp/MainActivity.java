package com.example.flashcardcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db var initialization
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        //local var with list of flashcards, saves data to the database (db)
        allFlashcards = flashcardDatabase.getAllCards();

        //fxn checks db to see if there are any saved flashcards b4 showing default
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());

        }
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


        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 100);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
                if(data != null) {
                    //this 100 needs to match the original 100 set at startActivityForResult
                    String newQuestion = data.getExtras().getString("FCQuestion");
                    String newAnswer = data.getExtras().getString("FCAnswer");

                    ((TextView) findViewById(R.id.flashcard_question)).setText(newQuestion);
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(newAnswer);

                    flashcardDatabase.insertCard(new Flashcard(newQuestion, newAnswer));

                }


        }
    }

}
