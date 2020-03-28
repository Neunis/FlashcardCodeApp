package com.example.flashcardcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //the actual database db
    FlashcardDatabase flashcardDatabase;
    //Data object that serves as a db copy
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

                    if (findViewById(R.id.flashcard_question).getVisibility() == View.INVISIBLE) {
                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                    }

                    //creates a new record in db
                    flashcardDatabase.insertCard(new Flashcard(newQuestion, newAnswer));
                    //updates copy (temp) of db
                    allFlashcards = flashcardDatabase.getAllCards();


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
                View answerSideView = findViewById(R.id.flashcard_answer);
                View questionSideView = findViewById(R.id.flashcard_question);

// get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

// get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

// hide the question and show the answer to prepare for playing the animation!
                questionSideView.setVisibility(View.INVISIBLE);
                answerSideView.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

    }

    //functions for button actions
    private void addButtonAction(){
        //initiates a new activity and send a signal expecting a response
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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

                //makes sure the next button is set to the right format of question first then answer
                if (findViewById(R.id.flashcard_question).getVisibility() == View.INVISIBLE) {
                    findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                    findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                }

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
