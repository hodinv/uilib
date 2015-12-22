package com.hodinv.uilib.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hodinv.uilib.ContentFragment;

/**
 * Created by vhodin on 17.12.2015.
 */
public class SubFragment extends ContentFragment {
    public static final String KEY = "key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return LayoutInflater.from(getContext()).inflate(R.layout.dummy, container, false);
    }

    @Override
    public String getTitle() {
        return "Child";
    }

    @Override
    public boolean hasLeftMenu() {
        return false;
    }
}
