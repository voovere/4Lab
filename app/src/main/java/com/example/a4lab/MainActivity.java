package com.example.a4lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SqliteHelper dbHelper;
    private ListView lvNotes;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity","onCreate() method started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d("MainActivity","onStart() method started");
        super.onStart();
        dbHelper = new SqliteHelper(getApplicationContext());
        notesList = new ArrayList<>();
        lvNotes = findViewById(R.id.lvNotes);

        for(Notes note : dbHelper.pullData()) {
            notesList.add(note.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        lvNotes.setAdapter(adapter);

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showCustomDialog(adapterView.getItemAtPosition(position).toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MainActivity","onCreateOptionsMenu() method started");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MainActivity","onOptionsItemSelected() method started");
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
                return true;
            case R.id.delete:
                startActivity(new Intent(MainActivity.this, DeleteNoteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void showCustomDialog(String title) {
        Log.d("MainActivity","showCustomDialog() method started");
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        TextView tvTitle = dialog.findViewById(R.id.tvTitleDialog);
        tvTitle.setText(title);
        TextView tvContent = dialog.findViewById(R.id.tvContentDialog);
        for(Notes note : dbHelper.pullData()) {
            if(title.equals(note.getTitle())){
                tvContent.setText(note.getContent());
            }
        }
        Button close = dialog.findViewById(R.id.bttnClose);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}