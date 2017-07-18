package com.mihail.rssreader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mihail.rssreader.ui.fragment.RssFragment;

import java.util.ArrayList;

/**
 * Created by Hell on 7/15/2017.
 */

public class RssViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<RssFragment> mRssFragments;

    public RssViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mRssFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mRssFragments.get(position);
    }

    @Override
    public int getCount() {
        return mRssFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void add(RssFragment rssFragment) {
        mRssFragments.add(rssFragment);
    }

    public void remove(int idx) {
        mRssFragments.remove(idx);
        notifyDataSetChanged();
    }

}
