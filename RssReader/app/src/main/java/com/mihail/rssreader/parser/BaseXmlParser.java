package com.mihail.rssreader.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hell on 7/16/2017.
 */

public abstract class BaseXmlParser<T> {

    protected abstract T parse(String tag);

    public abstract ArrayList<T> findAllObjects(String source);

    protected String findFirst(String tagName, String source, int startIdx) {
        if (tagName != null && source != null) {
            String regex = String.format("<%s>(.+?)<\\/%s>", tagName, tagName);
            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source);
            if (matcher.find(startIdx)) {
                return matcher.group(0);
            }
        }
        return null;
    }

    protected String getTagContent(String tagName, String source) {
        String regex = String.format("<%s.*?>(.+?)</%s>", tagName, tagName);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    protected ArrayList<T> findAll(String tagName, Class<T> type, String source) {
        ArrayList<T> results = new ArrayList<>();
        int startIdx = 0;
        while (true) {
            String foundStr = findFirst(tagName, source, startIdx);
            if (foundStr != null) {
                startIdx = source.indexOf(foundStr, startIdx) + foundStr.length();
                results.add(parse(foundStr));
            } else {
                break;
            }
        }
        return results;
    }

}
