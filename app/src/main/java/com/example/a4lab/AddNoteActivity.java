package com.example.a4lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private SqliteHelper dbHelper;
    private EditText title;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("AddNoteActivity","onCreate() method started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new SqliteHelper(getApplicationContext());
    }

    public void saveNote(View view) {
        Log.d("AddNoteActivity","saveNote() method started");
        title = findViewById(R.id.tfTitle);
        content = findViewById(R.id.tfContent);
        String stringTitle = title.getText().toString();
        String stringContent = content.getText().toString();

        if(stringTitle.length() != 0 && stringContent.length() != 0){
            if(!dbHelper.checkIfExists(stringTitle)){
                dbHelper.insertData(new Notes(stringTitle, stringContent));
                startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
            }
            else {
                Toast.makeText(getApplicationContext(),"Note with the same title already exists",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Please fill in all fields",Toast.LENGTH_SHORT).show();
        }
    }
}