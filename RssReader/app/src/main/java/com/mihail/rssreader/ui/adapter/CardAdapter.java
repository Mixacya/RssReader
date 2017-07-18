package com.mihail.rssreader.ui.adapter;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihail.rssreader.R;
import com.mihail.rssreader.data.BaseAsyncTask;
import com.mihail.rssreader.data.ImageReaderAsyncTask;
import com.mihail.rssreader.entity.Item;

import java.util.ArrayList;

import static android.text.Html.fromHtml;

/**
 * Created by Hell on 7/17/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardVH> {

    private ArrayList<Item> mPosts;
    private OnItemClickListener mOnItemClickListener;

    public CardAdapter(ArrayList<Item> posts) {
        this.mPosts = posts;
    }

    @Override
    public CardVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new CardVH(view);
    }

    @Override
    public void onBindViewHolder(CardVH holder, int position) {
        holder.ivImage.setImageDrawable(null);

        Item item = mPosts.get(position);
        holder.setItem(item);

        String title = item.getTitle();
        String subTitle = item.getPubDate();
        Spanned description = fromHtml(item.getDescription());
        Spanned text = Html.fromHtml(description.toString(), holder.imageGetter, null);

        holder.tvTitle.setText(title);
        holder.tvSubtitle.setText(subTitle);
        holder.tvDescription.setText(text);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Item item);
    }

    public interface OnFollowListener {
        void onFollow(final String link);
    }

    public class CardVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvSubtitle;
        TextView tvDescription;
        ImageView ivImage;

        private Item mItem;

        public CardVH(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tvSubtitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

            tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
            itemView.setOnClickListener(this);
        }

        public void setItem(Item item) {
            this.mItem = item;
        }

        final Html.ImageGetter imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String s) {
                new ImageReaderAsyncTask(mItem, drawableReaderCallback)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, s);
                return null;
            }
        };

        final BaseAsyncTask.ReaderCallback<Drawable> drawableReaderCallback =
                new BaseAsyncTask.ReaderCallback<Drawable>() {

            @Override
            public void onFinish(Drawable drawable) {
                if (drawable != null && ivImage.getDrawable() == null) {
                    ivImage.setImageDrawable(drawable);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        };

        @Override
        public void onClick(View view) {
            if (mItem != null) {
                mOnItemClickListener.onClick(mItem);
            }
        }
    }
}
