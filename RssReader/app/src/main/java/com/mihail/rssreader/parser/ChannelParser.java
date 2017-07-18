package com.mihail.rssreader.parser;

import com.mihail.rssreader.entity.Channel;
import com.mihail.rssreader.entity.Item;

import java.util.ArrayList;

/**
 * Created by Hell on 7/16/2017.
 */

public class ChannelParser extends BaseXmlParser<Channel> {

    public static final String ENTITY_NAME = "channel";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";

    private BaseXmlParser itemParser = XmlParserFactory.createParser(Item.class);

    ChannelParser() { }

    @Override
    protected Channel parse(String source) {
        Channel channel = new Channel();
        String title = getTagContent(TITLE, source);
        String link = getTagContent(LINK, source);
        String description = getTagContent(DESCRIPTION, source);
        channel.setTitle(title);
        channel.setLink(link);
        channel.setDescription(description);
        channel.setItems(itemParser.findAllObjects(source));
        return channel;
    }

    @Override
    public ArrayList<Channel> findAllObjects(String source) {
        return findAll(ENTITY_NAME, Channel.class, source);
    }
}
