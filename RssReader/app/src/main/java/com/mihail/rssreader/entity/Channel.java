package com.mihail.rssreader.entity;

import java.util.ArrayList;

/**
 * Created by Hell on 7/16/2017.
 */

public class Channel extends BaseEntity {

    private String mTitle;
    private String mSourceLink;
    private String mLink;
    private String mDescription;
    private int mTtl;
    private ArrayList<Item> mItems = new ArrayList<>();
    private String mManagingEditor;

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getTtl() {
        return mTtl;
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public String getManagingEditor() {
        return mManagingEditor;
    }

    public String getSourceLink() {
        return mSourceLink;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setTtl(int mTtl) {
        this.mTtl = mTtl;
    }

    public void setManagingEditor(String mManagingEditor) {
        this.mManagingEditor = mManagingEditor;
    }

    public void setSourceLink(String mSourceLink) {
        this.mSourceLink = mSourceLink;
    }

    public void setItems(ArrayList<Item> items) {
        this.mItems.clear();
        this.mItems.addAll(items);
    }

}
