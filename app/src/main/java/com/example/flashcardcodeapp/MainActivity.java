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

    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db var initialization
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        //local var with list of flashcards, saves data to the database (db)
        allFlashcards = flashcardDatabase.getAllCards();

        flashcardDbCheck();

        flashcardView();

        nextButtonAction();

        addButtonAction();

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

    //functions for flashcard in general (view, etc.)
    private void flashcardView(){
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
    }

    //functions for button actions
    private void addButtonAction(){
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 100);
            }

        });
    }

    private void nextButtonAction(){
        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progress to next card with addition of pointer index
                currentCardDisplayedIndex++;

                //checks to make sure that index stays in bounds if viewing last card in list
                if(currentCardDisplayedIndex > allFlashcards.size() -1){
                    currentCardDisplayedIndex = 0;
                }

                //sets Q&A TextViews w/ data from the db
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

            }
        });
    }

    //functions for database
    private void flashcardDbCheck(){
        //fxn checks db to see if there are any saved flashcards b4 showing default
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());

        }
    }

}
