package com.mihail.rssreader.data;

import com.mihail.rssreader.database.DatabaseManager;
import com.mihail.rssreader.entity.Channel;
import com.mihail.rssreader.entity.Item;
import com.mihail.rssreader.parser.BaseXmlParser;
import com.mihail.rssreader.parser.XmlParserFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hell on 7/17/2017.
 */

public class ItemReaderAsyncTask extends BaseAsyncTask<String, Item, ArrayList<Item>> {

    private int mChannelId;

    public ItemReaderAsyncTask(int channelId, ReaderCallback callback) {
        super(callback);
        this.mChannelId = channelId;
    }

    @Override
    protected ArrayList<Item> doInBackground(String... strings) {
        ArrayList<Item> cachedItems = DatabaseManager.getInstance().getItemDAO().getList(strings[0]);
        String response = null;
        try {
            response = HtmlReader.readPageContent(strings[0]);
        } catch (IOException e) {
            onError(e);
            return cachedItems;
        }
        BaseXmlParser<Channel> parser = XmlParserFactory.createParser(Channel.class);
        ArrayList<Channel> channels = parser.findAllObjects(response);
        if (channels.size() > 0) {
            Channel channel = channels.get(0);
            channel.setId(mChannelId);
            channel.setSourceLink(strings[0]);
            DatabaseManager.getInstance().getChannelDAO().update(channel);
            for (Item item : channel.getItems()) {
                item.setChannelLink(channel.getSourceLink());
                DatabaseManager.getInstance().getItemDAO().insert(item);
            }
            return channels.get(0).getItems();
        }
        return cachedItems;
    }
}
