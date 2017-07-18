package com.mihail.rssreader.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mihail.rssreader.database.DBHelper;
import com.mihail.rssreader.database.DatabaseManager;
import com.mihail.rssreader.entity.BaseEntity;

import java.util.ArrayList;

/**
 * Created by Hell on 7/16/2017.
 */

public abstract class GeneralDAO<T extends BaseEntity> {

    public static final String ID = "_id";

    public static final String ALL = "select * from %s";
    public static final String ITEM_BY_ID = "select * from %s where %s=%d";

    private SQLiteDatabase mDatabase;
    private String mTableName;
    private DBHelper mDbHelper;

    public GeneralDAO(SQLiteDatabase database, String tableName) {
        this.mDatabase = database;
        this.mTableName = tableName;
        mDbHelper = DatabaseManager.getInstance().getDbHelper();
    }

    public void update(T obj) {
        if (obj != null) {
            ContentValues cv = new ContentValues();
            writeToContentValues(cv, obj);
            String[] args = {String.valueOf(obj.getId())};
            mDatabase.update(mTableName, cv, ID + "=" + obj.getId(), null);
        }
    }

    public long insert(T obj) {
        if (obj != null && canBeInserted(obj)) {
            ContentValues cv = new ContentValues();
            writeToContentValues(cv, obj);
            long id = mDatabase.insert(mTableName, null, cv);
            obj.setId((int) id);
            return id;
        }
        return -1;
    }

    public void delete(int id) {
        if (id > 0) {
            mDatabase.delete(mTableName, ID + "=" + id, null);
        }
    }

    public T get(int id) {
        T obj = null;
        String query = String.format(ITEM_BY_ID, mTableName, ID, id);
        Cursor cursor = getRawQueryCursor(query);
        if (cursor != null && cursor.moveToFirst()) {
            obj = readFromCursor(cursor);
        }
        return obj;
    }

    public ArrayList<T> getList() {
        return getList(String.format(ALL, mTableName));
    }

    protected ArrayList<T> getList(String query) {
        ArrayList<T> list = new ArrayList<>();
        Cursor cursor = getRawQueryCursor(query);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(readFromCursor(cursor));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    protected Cursor getRawQueryCursor(String query) {
        mDatabase = mDbHelper.getWritableDatabase();
        return mDatabase.rawQuery(query, null);
    }

    protected boolean canBeInserted(T obj) {
        return true;
    }

    public abstract T readFromCursor(Cursor cursor);

    public abstract void writeToContentValues(ContentValues cv, T obj);

}
