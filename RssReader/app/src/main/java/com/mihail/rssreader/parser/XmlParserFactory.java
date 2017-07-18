package com.mihail.rssreader.parser;

import com.mihail.rssreader.entity.Channel;
import com.mihail.rssreader.entity.Item;

/**
 * Created by Hell on 7/16/2017.
 */

public class XmlParserFactory {

    public static BaseXmlParser createParser(Class objectClass) {
        BaseXmlParser xmlParser = null;
        if (objectClass.equals(Channel.class)) {
            xmlParser = new ChannelParser();
        } else if (objectClass.equals(Item.class)) {
            xmlParser = new ItemParser();
        }
        return xmlParser;
    }

}
