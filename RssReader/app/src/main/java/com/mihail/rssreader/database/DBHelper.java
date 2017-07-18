package com.mihail.rssreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mihail.rssreader.database.dao.ChannelDAO;
import com.mihail.rssreader.database.dao.ItemDAO;

/**
 * Created by Hell on 7/16/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "RssDatabase";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ChannelDAO.CREATE_TABLE);
        sqLiteDatabase.execSQL(ItemDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
