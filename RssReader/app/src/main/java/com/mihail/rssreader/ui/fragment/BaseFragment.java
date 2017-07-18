package com.mihail.rssreader.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hell on 7/14/2017.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        onCreateView(view, savedInstanceState);
        return view;
    }

    protected abstract void onCreateView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();
}
