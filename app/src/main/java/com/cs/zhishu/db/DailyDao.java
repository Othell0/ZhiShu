package com.cs.zhishu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by exbbefl on 7/15/2016.
 */
public class DailyDao {
    private DBOpenHelper helper;

    public DailyDao(Context context) {

        helper = DBOpenHelper.getInstance(context);
    }

    public void insertReadNew(String id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBConstant.COLUMN_ID, id);
        long insertNum = db.insert(DBConstant.TABLE_READ, null, cv);
    }

    public List<String> getAllReadNew() {

        List<String> list = new LinkedList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqlQuery = "select * from " + DBConstant.TABLE_READ;
        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                list.add(id);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

}
