package com.hodinv.uilib.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hodinv.uilib.ContentFragment;
import com.hodinv.uilib.utils.EnumsHelper;

/**
 * Demo of second available fragment in menu
 * Created by vhodin on 17.12.2015.
 */
public class SecondFragment extends ContentFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.dummy, container, false);
    }

    @Override
    public String getTitle() {
        return EnumsHelper.getInstance().getTitle(Titles.SECOND);
    }

    @Override
    public int getMenuId() {
        return R.id.menu_second;
    }

}
