package com.mihail.rssreader.ui.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mihail.rssreader.R;

/**
 * Created by Hell on 7/15/2017.
 */

public class DialogHelper {

    public static AlertDialog createRssAddDialog(Context context, final OnAddRssListener onAddRssListener) {

        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_add_rss, null);
        final EditText etTitle = (EditText) view.findViewById(R.id.etTitle);
        final EditText etRssLink = (EditText) view.findViewById(R.id.etRssLink);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view)
                .setPositiveButton(R.string.button_title_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = etTitle.getText().toString();
                        String url = etRssLink.getText().toString();
                        if (onAddRssListener != null) {
                            onAddRssListener.onAddRss(title, url);
                        }
                    }
                })
                .setNegativeButton(R.string.button_title_cancel, null);
        return alert.create();
    }

    public static AlertDialog closeTabDialog(Context context, final OnCloseTabListener onCloseTabListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_close)
                .setPositiveButton(R.string.button_title_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onCloseTabListener != null) {
                            onCloseTabListener.onClose();
                        }
                    }
                })
                .setNegativeButton(R.string.button_title_cancel, null);
        return alert.create();
    }

    public static AlertDialog deleteRssDialog(Context context, final OnRemoveTabListener onRemoveTabListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_remove)
                .setPositiveButton(R.string.button_title_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onRemoveTabListener != null) {
                            onRemoveTabListener.onRemove();
                        }
                    }
                })
                .setNegativeButton(R.string.button_title_cancel, null);
        return alert.create();
    }

    public interface OnAddRssListener {
        void onAddRss(String title, String url);
    }

    public interface OnCloseTabListener {
        void onClose();
    }

    public interface OnRemoveTabListener {
        void onRemove();
    }

}
