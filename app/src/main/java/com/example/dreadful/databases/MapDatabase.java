package com.example.dreadful.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dreadful.models.Map;

import java.util.ArrayList;

public class MapDatabase extends SQLiteOpenHelper {
    private static final String map_unique_id = "unique_id";
    private static final String map_table = "map_table";
    private static final String map_id = "map_id";
    private static final String map_name = "map_name";
    private static final String map_status = "map_status";
    private static final String map_exploredPercentage = "map_exploredPercentage";

    private static final int DATABASE_VERSION = 1;

    public MapDatabase(@Nullable Context context) {
        super(context, "map.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + map_table + " (" + map_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                map_unique_id + " TEXT, " + map_name + " TEXT, " + map_status + " INTEGER, " + map_exploredPercentage + " INTEGER)";
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

    public int mapCount() {
        int count = 0;
        String queryString = "SELECT * FROM " + map_table;
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

    public boolean doesDataExist() {
        int count = 0;
        String queryString = "SELECT * FROM " + map_table;
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

    public boolean addMap(Map map) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(map_unique_id, map.getUniqueId());
        contentValues.put(map_name, map.getName());
        contentValues.put(map_status, map.getStatus());
        contentValues.put(map_exploredPercentage, map.getExplorePercentage());

        long insert = db.insert(map_table, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Map> selectAll() {
        ArrayList<Map> allMap = new ArrayList<>();
        String queryString = "SELECT * FROM " + map_table;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String uniqueId = cursor.getString(1);
                String name = cursor.getString(2);
                int status = cursor.getInt(3);
                int exploredPercentage = cursor.getInt(4);

                allMap.add(new Map(id, uniqueId, name, status, 0, exploredPercentage, null));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return allMap;
    }

    public boolean deleteMap(Map map) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(map_table, map_id + " = ?", new String[]{String.valueOf(map.getId())});
        db.close();
        return deletedRows > 0;
    }

    public boolean deleteAllMap() {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(map_table, null, null);
        db.close();
        return deletedRows > 0;
    }

    public boolean updateMap(Map map) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(map_name, map.getName());
        contentValues.put(map_status, map.getStatus());
        contentValues.put(map_exploredPercentage, map.getExplorePercentage());

        String whereClause = map_id + "=?";

        String[] whereArgs = {String.valueOf(map.getId())};

        int result = db.update(map_table, contentValues, whereClause, whereArgs);

        return result > 0;
    }
}
