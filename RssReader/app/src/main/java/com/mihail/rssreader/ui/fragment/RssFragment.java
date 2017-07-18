package com.mihail.rssreader.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.mihail.rssreader.R;
import com.mihail.rssreader.data.ItemReaderAsyncTask;
import com.mihail.rssreader.database.DatabaseManager;
import com.mihail.rssreader.entity.Channel;
import com.mihail.rssreader.entity.Item;
import com.mihail.rssreader.entity.ItemComparatorFactory;
import com.mihail.rssreader.ui.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class RssFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_CHANNEL_ID = "ARG_CHANNEL_ID";

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mCardList;
    private CardAdapter mAdapter;
    private View mView;
    private ItemReaderAsyncTask mItemReader;
    private Channel mChannel;

    public RssFragment() { }

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int channelId = bundle.getInt(ARG_CHANNEL_ID);
        mChannel = DatabaseManager.getInstance().getChannelDAO().get(channelId);

        mView = view;
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srLayout);
        mRefreshLayout.setOnRefreshListener(this);
        mCardList = (RecyclerView) mView.findViewById(R.id.rvCardList);
        mCardList.setLayoutManager(new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new CardAdapter(mChannel.getItems());
        mAdapter.setOnItemClickListener(onItemClickListener);
        mCardList.setAdapter(mAdapter);

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        readItems();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopReadingItems();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rss;
    }

    @Override
    public void onRefresh() {
        readItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iSortAscending:
                Collections.sort(mChannel.getItems(),
                        ItemComparatorFactory.getComparator(ItemComparatorFactory.Order.ASC));
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.iSortDescending:
                Collections.sort(mChannel.getItems(),
                        ItemComparatorFactory.getComparator(ItemComparatorFactory.Order.DESC));
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getLink(int id) {
        Channel channel = DatabaseManager.getInstance().getChannelDAO().get(id);
        return channel.getLink();
    }

    private void readItems() {
        stopReadingItems();
        if (mChannel != null) {
            mItemReader = new ItemReaderAsyncTask(mChannel.getId(), mReaderCallback);
            mRefreshLayout.setRefreshing(true);
            mItemReader.execute(mChannel.getSourceLink());
        }
    }

    private void stopReadingItems() {
        if (mItemReader != null) {
            mItemReader.cancel(false);
            mItemReader = null;
        }
    }

    private final ItemReaderAsyncTask.ReaderCallback<ArrayList<Item>> mReaderCallback =
            new ItemReaderAsyncTask.ReaderCallback<ArrayList<Item>>() {

        @Override
        public void onFinish(ArrayList<Item> items) {
            mRefreshLayout.setRefreshing(false);
            mChannel.setItems(items);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onError(Throwable throwable) {
            Snackbar.make(mView, R.string.error, BaseTransientBottomBar.LENGTH_LONG);
        }
    };

    private final CardAdapter.OnItemClickListener onItemClickListener = new CardAdapter.OnItemClickListener() {
        @Override
        public void onClick(Item item) {
            String link = item.getLink();
            if (!link.startsWith("http://") && !link.startsWith("https://")) {
                link = "http://" + link;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intent);
        }
    };
}