package com.hodinv.uilib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by Vasily Hodin on 13.07.15.
 * Utils to manage keyboard - show/hide and other fi needed
 */
public class KeyboardHelper {

    /**
     * Hide software keyboard
     *
     * @param activity current activity
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
