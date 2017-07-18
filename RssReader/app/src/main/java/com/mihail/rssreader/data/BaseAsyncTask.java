package com.mihail.rssreader.data;

import android.os.AsyncTask;
import android.os.Handler;

/**
 * Created by Hell on 7/18/2017.
 */

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private ReaderCallback mCallback;
    private Handler mHandler;

    public BaseAsyncTask(ReaderCallback callback) {
        this.mCallback = callback;
        mHandler = new Handler();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (mCallback != null) {
            mCallback.onFinish(result);
        }
    }

    protected void onError(final Throwable throwable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onError(throwable);
                }
            }
        });
    }

    public interface ReaderCallback<Result> {
        void onFinish(Result result);
        void onError(Throwable throwable);
    }

}
