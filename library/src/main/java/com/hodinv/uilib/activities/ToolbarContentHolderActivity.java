package com.hodinv.uilib.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hodinv.uilib.ContentFragment;
import com.hodinv.uilib.R;

/**
 * Created by Vasily Hodin on 8.7.15.
 * Handles toolbar operations. Activity should remove standard action bar via theme
 * Toolbar id = lyt_toolbar
 */
public class ToolbarContentHolderActivity extends ProgressContentHolderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Toolbar mToolbar;


    /**
     * Setup UI in OnCreate
     */
    @Override
    public void setupUI() {
        super.setupUI();
        mToolbar = (Toolbar) findViewById(R.id.lyt_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    /**
     * Change the title associated with this activity.  If this is a
     * top-level activity, the title for its window will change.  If it
     * is an embedded activity, the parent can do whatever it wants
     * with it.
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    /**
     * Change the title associated with this activity.  If this is a
     * top-level activity, the title for its window will change.  If it
     * is an embedded activity, the parent can do whatever it wants
     * with it.
     */
    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mToolbar != null) {
            mToolbar.setTitle(titleId);
        }
    }

    /**
     * Occurs just after fragment changes - here can be updated for ex. title
     */
    @Override
    public void updateUI() {
        super.updateUI();
        ContentFragment currentFragment = getCurrentFragment();
        boolean showToolbar = currentFragment == null || currentFragment.showToolbar();
        if (mToolbar != null) {
            mToolbar.setVisibility(showToolbar ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Return base layout for activity
     * Layout should contain resource item with id=lyt_content
     * progressbar id = lyt_progress
     * progressbar title = txt_progress_title
     * Toolbar id = lyt_toolbar
     *
     * @return layout resource id
     */
    @Override
    public int getDefaultLayout() {
        return R.layout.activity_toolbar_progress_content_holder;
    }
}
