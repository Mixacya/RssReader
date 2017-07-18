package com.mihail.rssreader.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mihail.rssreader.entity.Item;

import java.util.ArrayList;

/**
 * Created by Hell on 7/16/2017.
 */

public class ItemDAO extends GeneralDAO<Item> {

    public static final String TABLE_NAME = "items";
    public static final String TITLE = "_title";
    public static final String LINK = "_link";
    public static final String DESCRIPTION = "_description";
    public static final String GUID = "_guid";
    public static final String PUB_DATE = "_pubDate";
    public static final String CATEGORY = "_category";
    public static final String CHANNEL_LINK = "_channel_link";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + ID +
            " integer primary key autoincrement, " + GUID + " text unique, " + TITLE + " text, " +
            LINK +" text, " + DESCRIPTION + " text, " + PUB_DATE + " text, " + CATEGORY + " text, " +
            CHANNEL_LINK + " text);";

    public static final String SELECT_BY_CHANNEL = "select * from " + TABLE_NAME +
            " where " + CHANNEL_LINK + " = '%s'";

    public static final String SELECT_BY_GUID = "select * from " + TABLE_NAME +
            " where " + GUID + " = '%s'";

    public ItemDAO(SQLiteDatabase database) {
        super(database, TABLE_NAME);
    }

    @Override
    public Item readFromCursor(Cursor cursor) {
        Item rssItem = new Item();
        if (cursor != null) {
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String title = cursor.getString(cursor.getColumnIndex(TITLE));
            String link = cursor.getString(cursor.getColumnIndex(LINK));
            String descriprion = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            String guid = cursor.getString(cursor.getColumnIndex(GUID));
            String pubDate = cursor.getString(cursor.getColumnIndex(PUB_DATE));
            String category = cursor.getString(cursor.getColumnIndex(CATEGORY));
            String channelLink = cursor.getString(cursor.getColumnIndex(CHANNEL_LINK));

            rssItem.setId(id);
            rssItem.setTitle(title);
            rssItem.setLink(link);
            rssItem.setDescription(descriprion);
            rssItem.setGuid(guid);
            rssItem.setPubDate(pubDate);
            rssItem.setCategory(category);
            rssItem.setChannelLink(channelLink);
        }
        return rssItem;
    }

    @Override
    public void writeToContentValues(ContentValues cv, Item item) {
        if (item.getId() > 0) {
            cv.put(ID, item.getId());
        }
        cv.put(TITLE, item.getTitle());
        cv.put(LINK, item.getLink());
        cv.put(DESCRIPTION, item.getDescription());
        cv.put(GUID, item.getGuid());
        cv.put(PUB_DATE, item.getPubDate());
        cv.put(CATEGORY, item.getCategory());
        cv.put(CHANNEL_LINK, item.getChannelLink());
    }

    @Override
    protected boolean canBeInserted(Item obj) {
        if (obj != null) {
            Cursor cursor = getRawQueryCursor(String.format(SELECT_BY_GUID, obj.getGuid()));
            return cursor.getCount() == 0;
        }
        return true;
    }

    public ArrayList<Item> getList(String channelLink) {
        return super.getList(String.format(SELECT_BY_CHANNEL, channelLink));
    }

}
