package com.mihail.rssreader;

import android.app.Application;

import com.mihail.rssreader.database.DatabaseManager;
import com.mihail.rssreader.helper.FileManager;

/**
 * Created by Hell on 7/16/2017.
 */

public class RssApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.getInstance().init(this);
        FileManager.getInstance().init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabaseManager.getInstance().deinit();
        FileManager.getInstance().deinit();
    }

}
