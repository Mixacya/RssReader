package com.mihail.rssreader.entity;

/**
 * Created by Hell on 7/16/2017.
 */

public abstract class BaseEntity {

    private int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}
