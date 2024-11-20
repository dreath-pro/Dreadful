package com.example.dreadful.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dreadful.models.Player;

import java.util.ArrayList;
import java.util.List;

public class MonsterDatabase extends SQLiteOpenHelper {
    private static final String monster_table = "monster_table";
    private static final String monster_id = "monster_id";
    private static final String monster_name = "monster_name";

    private static final int DATABASE_VERSION = 1;

    public MonsterDatabase(@Nullable Context context) {
        super(context, "monster.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + monster_table + " (" + monster_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                monster_name + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        switch (oldVersion) {
//            case 1:
//                // Upgrade from version 1 to 2
//                db.execSQL("ALTER TABLE " + monster_table + " ADD COLUMN new_column TEXT");
//                // Add more upgrade steps for version 2 if needed
//            case 2:
//                // Upgrade from version 2 to 3
//                // Add more upgrade steps for version 3 if needed
//            default:
//                break;
//        }
//        db.setVersion(newVersion);
    }

    public boolean doesDataExist() {
        int count = 0;
        String queryString = "SELECT * FROM " + monster_table;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return count > 0;
    }

    public int getMonsterCount() {
        int count = 0;
        String queryString = "SELECT * FROM " + monster_table;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return count;
    }

    public boolean addMonster(String monster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(monster_name, monster);

        long insert = db.insert(monster_table, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> selectAll() {
        ArrayList<String> allMonster = new ArrayList<>();
        String queryString = "SELECT * FROM " + monster_table;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                allMonster.add(name);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return allMonster;
    }
}
