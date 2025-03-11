package com.example.dreadful.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dreadful.characters.Flamethrower;
import com.example.dreadful.logics.SetupCharacter;
import com.example.dreadful.models.Map;
import com.example.dreadful.models.Monster;

import java.util.ArrayList;

public class MonsterDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String unique_id = "unique_id";
    private static final String monster_table = "monster_table";
    private static final String monster_id = "monster_id";
    private static final String monster_name = "monster_name";

    private SetupCharacter setupCharacter;

    private static final int DATABASE_VERSION = 1;

    public MonsterDatabase(@Nullable Context context) {
        super(context, "monster.db", null, DATABASE_VERSION);
        this.context = context;
        setupCharacter = new SetupCharacter(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + monster_table + " (" + monster_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                unique_id + " TEXT, " + monster_name + " TEXT)";
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

    public int monsterCount() {
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

    public boolean doesSelectedDataExist(String uniqueId) {
        int count = 0;
        String queryString = "SELECT * FROM " + monster_table + " WHERE " + unique_id + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {uniqueId};

        Cursor cursor = db.rawQuery(queryString, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return count > 0;
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

    public boolean addMonster(Monster monster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(unique_id, monster.getUniqueId());
        contentValues.put(monster_name, monster.getName());

        long insert = db.insert(monster_table, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Monster> selectAll() {
        ArrayList<Monster> allMonster = new ArrayList<>();
        String queryString = "SELECT * FROM " + monster_table;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String uniqueId = cursor.getString(1);
                allMonster.add(getMonsterFromSource(uniqueId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return allMonster;
    }

    private Monster getMonsterFromSource(String unique_id) {
        Monster finalMonster = new Flamethrower(context);
        ArrayList<Monster> monsters = setupCharacter.getMonsterListing();

        for (Monster monster : monsters) {
            if (unique_id.equals(monster.getUniqueId())) {
                finalMonster = monster;
            }
        }

        return finalMonster;
    }

    public boolean deleteMonster(Monster monster) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(monster_table, unique_id + " = ?", new String[]{String.valueOf(monster.getUniqueId())});
        db.close();
        return deletedRows > 0;
    }

    public boolean deleteAllMonster() {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(monster_table, null, null);
        db.close();
        return deletedRows > 0;
    }

    public boolean updateMonster(Monster monster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(monster_name, monster.getName());
        String whereClause = unique_id + "=?";
        String[] whereArgs = {String.valueOf(monster.getUniqueId())};
        int result = db.update(monster_table, contentValues, whereClause, whereArgs);

        return result > 0;
    }
}
