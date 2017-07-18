package com.mihail.rssreader.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mihail.rssreader.R;
import com.mihail.rssreader.database.DatabaseManager;
import com.mihail.rssreader.database.dao.ChannelDAO;
import com.mihail.rssreader.database.dao.GeneralDAO;
import com.mihail.rssreader.entity.Channel;
import com.mihail.rssreader.ui.adapter.RssViewPagerAdapter;
import com.mihail.rssreader.ui.fragment.RssFragment;
import com.mihail.rssreader.ui.helper.DialogHelper;

import java.util.ArrayList;

import static com.mihail.rssreader.R.id.vpContainer;
import static com.mihail.rssreader.ui.fragment.RssFragment.ARG_CHANNEL_ID;

public class MenuActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mContainer;
    private NavigationView mNavigationView;
    private RssViewPagerAdapter mRssAdapter;
    private FragmentManager mFragmentManager;

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mContainer = (ViewPager) findViewById(vpContainer);

        setToolbar(mToolbar);
        mNavigationView = getNavigationView();
        mFragmentManager = getSupportFragmentManager();
        mRssAdapter = new RssViewPagerAdapter(mFragmentManager);

        mTabLayout.setTabGravity(View.TEXT_ALIGNMENT_GRAVITY);
        mContainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);

        readMenuItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iClose:
                if (mTabLayout.getTabCount() > 0) {
                    DialogHelper.closeTabDialog(this, onCloseTabListener).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void navigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iAddRss) {
            DialogHelper.createRssAddDialog(this, onAddRssListener).show();
        } else {
            if (mTabLayout.getTabCount() == 0) {
                mTabLayout.setVisibility(View.VISIBLE);
            }
            ChannelDAO dao = DatabaseManager.getInstance().getChannelDAO();
            Channel channel = dao.get(item.getItemId());
            if (mTabLayout.getTabCount() == 0) {
                addAndSelectTab(channel, item);
            } else {
                boolean isContains = false;
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    if (mTabLayout.getTabAt(i).getText().toString().equalsIgnoreCase(item.getTitle().toString())) {
                        isContains = true;
                        mTabLayout.getTabAt(i).select();
                        break;
                    }
                }
                if (!isContains) {
                    addAndSelectTab(channel, item);
                }
            }
        }
    }

    private void addAndSelectTab(Channel channel, MenuItem item) {
        if (channel != null) {
            TabLayout.Tab newTab = mTabLayout.newTab().setText(item.getTitle());
            mTabLayout.addTab(newTab);

            RssFragment fragment = new RssFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_CHANNEL_ID, channel.getId());
            fragment.setArguments(bundle);
            mRssAdapter.add(fragment);
            mContainer.setAdapter(mRssAdapter);

            newTab.select();
            mContainer.setCurrentItem(newTab.getPosition());
        } else {
            Snackbar.make(mContainer, "Can't be opened", Snackbar.LENGTH_LONG).show();
        }
    }

    private void makeMenuItem(String title, String url) {
        Channel channel = new Channel();
        channel.setTitle(title);
        channel.setSourceLink(url);
        GeneralDAO dao = DatabaseManager.getInstance().getChannelDAO();
        long id = dao.insert(channel);
        if (id > -1) {
            addMenuItem(channel, mNavigationView.getMenu());
        } else {
            Snackbar.make(mContainer, "Can't be added", Snackbar.LENGTH_LONG).show();
        }
    }

    private void readMenuItems() {
        ChannelDAO dao = DatabaseManager.getInstance().getChannelDAO();
        if (dao == null) {
            return;
        }
        final Menu menu = mNavigationView.getMenu();
        if (menu != null) {
            menu.removeGroup(1);
            ArrayList<Channel> channelList = dao.getList();
            for (Channel item : channelList) {
                addMenuItem(item, menu);
            }
        }
    }

    private void addMenuItem(Channel item, final Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getTitle().equals(item.getTitle())) {
                return;
            }
        }
        MenuItem menuItem = menu.add(1, item.getId(), item.getId(), item.getTitle());

        ImageButton btnRemove = new ImageButton(this);
        btnRemove.setImageResource(R.drawable.ic_clear_black_24dp);
        btnRemove.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.removeItem(view.getId());
                DatabaseManager.getInstance().getChannelDAO().delete(view.getId());
            }
        });
        menuItem.setActionView(btnRemove);
    }

    private final TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mContainer.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) { }

        @Override
        public void onTabReselected(TabLayout.Tab tab) { }
    };

    private final DialogHelper.OnAddRssListener onAddRssListener = new DialogHelper.OnAddRssListener() {
        @Override
        public void onAddRss(String title, String url) {
            makeMenuItem(title, url);
        }
    };

    private final DialogHelper.OnCloseTabListener onCloseTabListener = new DialogHelper.OnCloseTabListener() {
        @Override
        public void onClose() {
            int selectedPosition = mTabLayout.getSelectedTabPosition();
            if (selectedPosition > -1) {
                mTabLayout.removeTabAt(selectedPosition);
                mRssAdapter.remove(selectedPosition);
            }

            if (mTabLayout.getTabCount() == 0) {
                mTabLayout.setVisibility(View.GONE);
            }
        }
    };
}
