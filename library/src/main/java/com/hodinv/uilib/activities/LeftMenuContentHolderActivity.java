package com.hodinv.uilib.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.hodinv.uilib.ContentFragment;
import com.hodinv.uilib.R;
import com.hodinv.uilib.utils.KeyboardHelper;

/**
 * Created by Vasily Hodin on 8.7.15.
 * Handles left menu processing.
 * left menu id = view_left_menu
 * drawer id = lyt_drawer
 */
public class LeftMenuContentHolderActivity extends ToolbarContentHolderActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mMenuEnabled = true;

    /**
     * Return base layout for activity
     * Layout should contain resource item with id=lyt_content
     * progressbar id = lyt_progress
     * progressbar title = txt_progress_title
     * Toolbar id = lyt_toolbar
     * left menu id = view_left_menu
     * drawer id = lyt_drawer
     *
     * @return layout resource id
     */
    @Override
    public int getDefaultLayout() {
        return R.layout.activity_left_menu_content_holder;
    }

    /**
     * Return resource ids for menu items
     *
     * @return array of ids of items in menu processed as menu items (can be pressed and selected)
     */
    public int[] getMenuIds() {

        return new int[0];
    }


    /**
     * Returns layout id for menu
     *
     * @return layout resource to use as menu (will be inflated into menu area)
     */
    public int getMenuLayout() {
        return R.layout.empty_menu;
    }

    private int[] mMenuIds;

    private FrameLayout mLeftMenu;


    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if (KeyEvent.KEYCODE_MENU == keyCode) {
            if (mMenuEnabled) {
                toggleMenu();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * Setup UI in OnCreate
     */
    @Override
    public void setupUI() {
        super.setupUI();
        mMenuIds = getMenuIds();
        final View mainView = findViewById(R.id.lyt_activity_content);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.lyt_drawer);
        if (mDrawerLayout != null && getSupportActionBar() != null) {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    android.R.string.untitled, android.R.string.untitled) {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                    mainView.setTranslationX(slideOffset * drawerView.getWidth());
                    mDrawerLayout.bringChildToFront(drawerView);
                    mDrawerLayout.requestLayout();
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerLayout.setDrawerShadow(R.drawable.menu_shadow, GravityCompat.START);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mLeftMenu = (FrameLayout) findViewById(R.id.view_left_menu);
        mLeftMenu.addView(LayoutInflater.from(this).inflate(getMenuLayout(), mLeftMenu, false));
        // setup menu callbacks
        for (int id : mMenuIds) {
            View view = mLeftMenu.findViewById(id);
            if (view != null) {
                view.setOnClickListener(mOnMenuItemClicked);
            }
        }
    }


    /**
     * Occurs just after fragment changes - here can be updated for ex. title
     */
    @Override
    public void updateUI() {
        super.updateUI();
        int currentMenuId = getCurrentMenuId();
        for (int id : mMenuIds) {
            View view = findViewById(id);
            if (view != null) {
                view.setSelected(id == currentMenuId);
            }
        }
        ContentFragment fragment = getCurrentFragment();
        if (fragment != null) {
            setMenuEnabled(fragment.hasLeftMenu());
        }
    }

    /**
     * Gets current menu id from curent fragment is already loaded
     *
     * @return current menu item id
     */
    private int getCurrentMenuId() {
        ContentFragment fragment = getCurrentFragment();
        return fragment == null ? 0 : fragment.getMenuId();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item))) {
            KeyboardHelper.hideKeyboard(this);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            // SOFT back
            back(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    private View.OnClickListener mOnMenuItemClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onLeftMenuSelected(v.getId());
            mDrawerLayout.closeDrawers();
        }
    };

    /**
     * Toggle left menu on-off
     */
    @Override
    public void toggleMenu() {
        if (mDrawerLayout.isDrawerOpen(mLeftMenu)) {
            mDrawerLayout.closeDrawer(mLeftMenu);
        } else {
            mDrawerLayout.openDrawer(mLeftMenu);
        }

    }

    /**
     * Enable ot disable left menu in activity if has one
     *
     * @param enabled true to enable menu
     */
    @Override
    public void setMenuEnabled(boolean enabled) {
        mMenuEnabled = enabled;
        processMenuEnabling(enabled);
    }

    private void processMenuEnabling(boolean enabled) {
        if (mDrawerLayout != null) {
            if (!enabled) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                mDrawerToggle.setDrawerIndicatorEnabled(false);
            } else {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
            }

        }
    }

    /**
     * Makes progress visible with no title
     */
    @Override
    public void showProgress() {
        super.showProgress();
        processMenuEnabling(false);
    }

    /**
     * Show progress with title
     *
     * @param titleId string resource for progress title
     */
    @Override
    public void showProgress(int titleId) {
        super.showProgress(titleId);
        processMenuEnabling(false);
    }

    /**
     * Show progress with title
     *
     * @param title progress title
     */
    @Override
    public void showProgress(String title) {
        super.showProgress(title);
        processMenuEnabling(false);
    }


    /**
     * Hides progress
     */
    @Override
    public void hideProgress() {
        super.hideProgress();
        processMenuEnabling(mMenuEnabled);
    }

    /**
     * Clears fragment stack and start new fragment
     *
     * @param contentFragment fragment to start
     */
    @Override
    public void startFragment(ContentFragment contentFragment) {
        super.startFragment(contentFragment);
        setMenuEnabled(contentFragment.hasLeftMenu());
    }

    /**
     * Starts new fragment, prev saved in backstack
     *
     * @param contentFragment fragment to start
     */
    @Override
    public void startFragmentWithStacking(ContentFragment contentFragment) {
        super.startFragmentWithStacking(contentFragment);
        setMenuEnabled(contentFragment.hasLeftMenu());
    }

    /**
     * Implement on menu item press action
     *
     * @param menuId menu item pressed
     *               #return true if item should be selected
     */
    protected void onLeftMenuSelected(int menuId) {

    }

    /**
     * Return if menu is currently enabled
     *
     * @return true of menu is enabled
     */
    protected boolean isMenuEnabled() {
        return mMenuEnabled;
    }
}
