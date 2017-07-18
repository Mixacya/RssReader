package com.mihail.rssreader.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Hell on 7/16/2017.
 */

public class HtmlReader {

    public static String readPageContent(String link) throws IOException {
        String result = "";
        URL url = new URL(link);
        URLConnection urlConnection = url.openConnection();
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return result;
    }
}
