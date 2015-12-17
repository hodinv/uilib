package com.hodinv.uilib.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Vasily Hodin on 13.07.15.
 * Helper for creating standard dialogs
 */
public class DialogHelper {

    /**
     * Create simple dialog with cancel button
     *
     * @param context context for dialog
     * @param title   dialog title
     * @param message dialog body
     * @return shown dilaog
     */
    public static AlertDialog showAlert(Context context, String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).create();
        dialog.show();
        return dialog;
    }


    /**
     * Create simple dialog with ok button and callback on ok
     *
     * @param context context for dialog
     * @param title   dialog title
     * @param message dialog body
     * @param onOk    callback for ok button
     */
    public static void showAlert(Context context, String title, String message, final Runnable onOk) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onOk.run();
            }
        }).setCancelable(true).show();

    }


    /**
     * Show selection dialog
     *
     * @param context  dialog contextr
     * @param title    dialog title
     * @param values   map with values and titles
     * @param callback callback after select
     * @param <T>      value type
     */
    public static <T> void showSelect(Context context, String title, Map<T, String> values, T current, final OnSelect<T> callback) {
        final ArrayList<T> keys = new ArrayList<>(values.keySet());
        final String[] titles = new String[keys.size()];
        int currentPos = -1;
        for (int i = 0; i < keys.size(); i++) {
            titles[i] = values.get(keys.get(i));
            if (keys.get(i).equals(current)) {
                currentPos = i;
            }
        }
        new AlertDialog.Builder(context).setSingleChoiceItems(titles, currentPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which >= 0 && which < keys.size()) {
                    callback.onSelect(keys.get(which));
                }
            }
        }).setTitle(title).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).show();
    }

    /**
     * Shows confirmation dialog
     *
     * @param context dialog contect
     * @param title   dialog title
     * @param message dialog content message
     * @param okLabel lable for positive action
     * @param onOk    positive action
     */
    public static void askConfirm(Context context, String title, String message, String okLabel, final Runnable onOk) {
        if (context == null) {
            return;
        }
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onOk.run();
            }
        }).setCancelable(true).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public interface OnSelect<T> {
        void onSelect(T value);
    }
}
