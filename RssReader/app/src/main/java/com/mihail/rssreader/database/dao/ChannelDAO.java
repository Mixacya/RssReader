package com.mihail.rssreader.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mihail.rssreader.entity.Channel;

/**
 * Created by Hell on 7/17/2017.
 */

public class ChannelDAO extends GeneralDAO<Channel> {

    public static final String TABLE_NAME = "channels";

    public static final String TITLE = "_title";
    public static final String LINK = "_link";
    public static final String SOURCE_LINK = "_source_link";

    public static final String ID_BY_ITEM = "select %s from %s where %s='%s' and %s='%s'";
    public static final String SELECT_BY_SOURCE_LINK = "select * from " + TABLE_NAME + " where "
            + SOURCE_LINK + "='%s'";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + ID +
            " integer primary key autoincrement, " + TITLE + " text, " + LINK +" text, " +
            SOURCE_LINK +" text);";

    public ChannelDAO(SQLiteDatabase database) {
        super(database, TABLE_NAME);
    }

    @Override
    public Channel readFromCursor(Cursor cursor) {
        Channel channel = new Channel();
        if (cursor != null) {
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String title = cursor.getString(cursor.getColumnIndex(TITLE));
            String link = cursor.getString(cursor.getColumnIndex(LINK));
            String sourceLink = cursor.getString(cursor.getColumnIndex(SOURCE_LINK));

            channel.setId(id);
            channel.setTitle(title);
            channel.setLink(link);
            channel.setSourceLink(sourceLink);
        }
        return channel;
    }

    @Override
    public void writeToContentValues(ContentValues cv, Channel channel) {
        if (channel.getId() > 0) {
            cv.put(ID, channel.getId());
        }
        cv.put(TITLE, channel.getTitle());
        cv.put(LINK, channel.getLink());
        cv.put(SOURCE_LINK, channel.getSourceLink());
    }

    @Override
    protected boolean canBeInserted(Channel obj) {
        if (obj != null) {
            Cursor cursor = getRawQueryCursor(String.format(SELECT_BY_SOURCE_LINK, obj.getSourceLink()));
            return cursor.getCount() == 0;
        }
        return super.canBeInserted(obj);
    }
}
