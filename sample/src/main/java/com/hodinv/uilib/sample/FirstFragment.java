package com.hodinv.uilib.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hodinv.uilib.ContentFragment;

/**
 * Created by vhodin on 17.12.2015.
 */
public class FirstFragment extends ContentFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.activity_main, container, false);
    }

    @Override
    public String getTitle() {
        return "Hello";
    }

    @Override
    public void updateUI() {
        super.updateUI();
        showProgress("Hello");
    }
}
