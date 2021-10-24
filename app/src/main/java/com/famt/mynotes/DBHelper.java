package com.famt.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String dbName = "notes_db";
    public static final String tableName = "notes_table";

    public DBHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE " + tableName + "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(DB);
    }

    public Boolean insertNote(Note note) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle().trim());
        contentValues.put("description", note.getDescription().trim());
        contentValues.put("time", note.getCreatedTime());
        long result = DB.insert(tableName, null, contentValues);
        return result != -1;
    }


    public Boolean updateNote(Note note) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", note.getId());
        contentValues.put("title", note.getTitle());
        contentValues.put("description", note.getDescription());
        contentValues.put("time", note.getCreatedTime());
        Cursor cursor = DB.rawQuery("SELECT * FROM " + tableName + " WHERE id = ?", new String[]{String.valueOf(note.getId())});
        if (cursor.getCount() > 0) {
            long result = DB.update(tableName, contentValues, "id=?", new String[]{String.valueOf(note.getId())});
            return result != -1;
        } else {
            return false;
        }
    }


    public Boolean deletedata(int id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + tableName + " WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = DB.delete(tableName, "id=?", new String[]{String.valueOf(id)});
            return result != -1;
        } else {
            return false;
        }

    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + tableName + " ORDER BY time DESC", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                long time = Long.parseLong(cursor.getString(cursor.getColumnIndex("time")));
                notes.add(new Note(id, title, description, time));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;

    }
}
