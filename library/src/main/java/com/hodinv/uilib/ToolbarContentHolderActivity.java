package com.hodinv.uilib;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Vasily Hodin on 8.7.15.
 * Handles toolar operations. Activity should remove standart action bar via theme
 * Toolbar id = lyt_toolbar
 */
public class ToolbarContentHolderActivity extends ProgressContentHolderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Toolbar mToolbar;

    @Override
    public void setupUI() {
        super.setupUI();
        mToolbar = (Toolbar) findViewById(R.id.lyt_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mToolbar != null) {
            mToolbar.setTitle(titleId);
        }
    }

    @Override
    protected void updateUI() {
        super.updateUI();
        ContentFragment currentFragment = getCurrentFragment();
        boolean showToolbar = currentFragment == null || currentFragment.showToolbar();
        if (mToolbar != null) {
            mToolbar.setVisibility(showToolbar ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getDefaultLayout() {
        return R.layout.activity_toolbar_progress_content_holder;
    }
}
