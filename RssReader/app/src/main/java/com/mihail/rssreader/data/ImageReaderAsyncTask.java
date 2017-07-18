package com.mihail.rssreader.data;

import android.graphics.drawable.Drawable;
import android.webkit.URLUtil;

import com.mihail.rssreader.entity.Item;
import com.mihail.rssreader.helper.FileManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Hell on 7/18/2017.
 */

public class ImageReaderAsyncTask extends BaseAsyncTask<String, Void, Drawable> {

    private Item mItem;

    public ImageReaderAsyncTask(Item item, ReaderCallback callback) {
        super(callback);
        this.mItem = item;
    }

    @Override
    protected Drawable doInBackground(String... strings) {
        final FileManager fileManager = FileManager.getInstance();

        String fileName = URLUtil.guessFileName(mItem.getLink(), null, null);

        File imgFile = fileManager.getCacheFile(fileName);
        if (strings != null && strings.length > 0 && !imgFile.exists()) {
            try {
                URL url = new URL(strings[0]);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                fileManager.saveFile(is, fileName);
                is.close();
            } catch (IOException e) {
                onError(e);
            }
        }
        return Drawable.createFromPath(imgFile.getAbsolutePath());
    }

}
