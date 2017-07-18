package com.mihail.rssreader.entity;

/**
 * Created by Hell on 7/16/2017.
 */

public class Item extends BaseEntity {

    private String title;
    private String link;
    private String description;
    private String guid;
    private String pubDate; // maybe change to date;
    private String category;
    private String channelLink;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getGuid() {
        return guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getCategory() {
        return category;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

}
