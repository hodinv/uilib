package com.hodinv.uilib.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hodinv.uilib.ContentFragment;
import com.hodinv.uilib.utils.EnumsHelper;

/**
 * Created by vhodin on 17.12.2015.
 */
public class FirstFragment extends ContentFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return LayoutInflater.from(getContext()).inflate(R.layout.dummy, container, false);
    }

    @Override
    public String getTitle() {
        return EnumsHelper.getInstance().getTitle(Titles.FIRST);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sub) {
            startFragmentWithStacking(new SubFragment().arguments().putString(SubFragment.KEY, "info").build());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getMenuId() {
        return R.id.menu_first;
    }
}
