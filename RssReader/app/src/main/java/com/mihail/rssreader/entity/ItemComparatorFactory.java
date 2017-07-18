package com.mihail.rssreader.entity;

import java.util.Comparator;

/**
 * Created by Hell on 7/18/2017.
 */

public class ItemComparatorFactory {

    public enum Order { ASC, DESC }

    public static Comparator<Item> getComparator(Order order) {
        switch (order) {
            case ASC:
                return new ItemComparatorAscending();
            case DESC:
                return new ItemComparatorDescending();
        }
        return null;
    }

    private static class ItemComparatorAscending implements Comparator<Item> {

        @Override
        public int compare(Item item1, Item item2) {
            int result = 0;
            if (item1 != null && item2 != null) {
                result = item1.getPubDate().compareTo(item2.getPubDate());
                if (result == 0) {
                    result = item1.getTitle().compareTo(item2.getTitle());
                }
            }
            return result;
        }
    }

    private static class ItemComparatorDescending extends ItemComparatorAscending {

        @Override
        public int compare(Item item1, Item item2) {
            return -super.compare(item1, item2);
        }
    }

}
