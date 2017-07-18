package com.mihail.rssreader.parser;

import com.mihail.rssreader.entity.Item;

import java.util.ArrayList;

/**
 * Created by Hell on 7/16/2017.
 */

public class ItemParser extends BaseXmlParser<Item> {

    public static final String ENTITY_NAME = "item";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final String GUID = "guid";
    public static final String PUB_DATE = "pubDate";
    public static final String CATEGORY = "category";

    ItemParser() { }

    @Override
    protected Item parse(String source) {

        String title = getTagContent(TITLE, source);
        String link = getTagContent(LINK, source);
        String description = getTagContent(DESCRIPTION, source);
        String guid = getTagContent(GUID, source);
        String pubDate = getTagContent(PUB_DATE, source);
        String category = getTagContent(CATEGORY, source);

        Item item = new Item();
        item.setTitle(title);
        item.setLink(link);
        item.setDescription(description);
        item.setGuid(guid);
        item.setPubDate(pubDate);
        item.setCategory(category);

        return item;
    }

    @Override
    public ArrayList<Item> findAllObjects(String source) {
        return findAll(ENTITY_NAME, Item.class, source);
    }
}
