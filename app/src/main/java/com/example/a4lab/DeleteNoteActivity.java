package com.example.a4lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {

    private SqliteHelper dbHelper;
    private Spinner spinner;
    private ArrayList<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DeleteNoteActivity","onCreate() method started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);
    }

    @Override
    protected void onStart() {
        Log.d("DeleteNoteActivity","onStart() method started");
        super.onStart();
        dbHelper = new SqliteHelper(getApplicationContext());

        titleList = new ArrayList<>();
        for(Notes note : dbHelper.pullData()) {
            titleList.add(note.getTitle());
        }

        spinner = findViewById(R.id.spinner);

        ArrayAdapter adapter= new ArrayAdapter<String>(DeleteNoteActivity.this ,android.R.layout.simple_spinner_item, titleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void delete(View view) {
        Log.d("DeleteNoteActivity","delete() method started");
        dbHelper.deleteData(spinner.getSelectedItem().toString());
        startActivity(new Intent(DeleteNoteActivity.this, MainActivity.class));
    }

    public void deleteAll(View view) {
        Log.d("DeleteNoteActivity","deleteAll() method started");
        dbHelper.deleteAllData();
        startActivity(new Intent(DeleteNoteActivity.this, MainActivity.class));
    }
}