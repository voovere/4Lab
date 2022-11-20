package com.example.a4lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {
    public SqliteHelper(@Nullable Context context) {
        super(context, "Notes.db", null, 21);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("SQLiteHelper","onCreate() method started");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Notes (ID integer primary key autoincrement, Title text, Content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("SQLiteHelper","onUpgrade() method started");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Notes");
        onCreate(sqLiteDatabase);
    }

    public void insertData(Notes note){
        Log.d("SQLiteHelper","insertData() method started");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", note.getTitle());
        contentValues.put("Content", note.getContent());
        sqLiteDatabase.insert("Notes", null, contentValues);
        sqLiteDatabase.close();
    }

    public void deleteData(String title){
        Log.d("SQLiteHelper","deleteData() method started");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("Notes", "Title=?", new String[]{title});
        sqLiteDatabase.close();
    }

    public void deleteAllData(){
        Log.d("SQLiteHelper","deleteAllData() method started");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM Notes");
        sqLiteDatabase.close();
    }

    public ArrayList<Notes> pullData() {
        Log.d("SQLiteHelper","pullData() method started");
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorNotes = sqLiteDatabase.rawQuery("SELECT * FROM Notes", null);
        ArrayList<Notes> noteModalArrayList = new ArrayList<>();
        if (cursorNotes.moveToFirst()) {
            do {
                noteModalArrayList.add(new Notes(cursorNotes.getString(1), cursorNotes.getString(2)));
            } while (cursorNotes.moveToNext());
        }
        cursorNotes.close();
        return noteModalArrayList;
    }

    public boolean checkIfExists(String title){
        Log.d("SQLiteHelper","checkIfExists() method started");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        for(Notes note : pullData()) {
            if (title.equals(note.getTitle())) {
                return true;
            }
        }
        sqLiteDatabase.close();
        return false;
    }
}
