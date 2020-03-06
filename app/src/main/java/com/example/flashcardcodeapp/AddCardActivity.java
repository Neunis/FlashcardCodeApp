package com.example.flashcardcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               finish();
            }
        });

        findViewById(R.id.flashcard_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionInput = (((EditText) findViewById(R.id.editFCQuestion)).getText().toString());
                String questionOutput = (((EditText) findViewById(R.id.editFCAnswer)).getText().toString());

                Intent data = new Intent(); //creates a new intent

                data.putExtra("FCQuestion", questionInput);
                data.putExtra("FCAnswer", questionOutput);

                setResult(RESULT_OK, data); //set result code

                finish(); //closes activity and pass data to MainActivity
            }
        });
    }
}
