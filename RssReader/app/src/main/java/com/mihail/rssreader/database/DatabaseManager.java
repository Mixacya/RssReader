package com.mihail.rssreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mihail.rssreader.database.dao.ItemDAO;
import com.mihail.rssreader.database.dao.ChannelDAO;

import java.lang.ref.WeakReference;

/**
 * Created by Hell on 7/16/2017.
 */

public class DatabaseManager {

    private static DatabaseManager instance;

    private ItemDAO mItemDAO;
    private ChannelDAO mChannelDAO;
    private DBHelper dbHelper;
    private WeakReference<Context> mContextReference;

    private DatabaseManager() { }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init(Context context) {
        mContextReference = new WeakReference<>(context);
    }

    public void deinit() {
        mContextReference.clear();
    }

    public ItemDAO getItemDAO() {
        if (mItemDAO == null) {
            SQLiteDatabase database = getDbHelper().getWritableDatabase();
            mItemDAO = new ItemDAO(database);
        }
        return mItemDAO;
    }

    public ChannelDAO getChannelDAO() {
        if (mChannelDAO == null) {
            SQLiteDatabase database = getDbHelper().getWritableDatabase();
            mChannelDAO = new ChannelDAO(database);
        }
        return mChannelDAO;
    }

    public DBHelper getDbHelper() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(mContextReference.get());
        }
        return dbHelper;
    }


}
