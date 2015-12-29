package com.hodinv.uilib;

/**
 * Base interface to communicate with holding activity
 * Created by vhodin on 17.12.2015.
 */
public interface ContentFragmentHolder {
    /**
     * Clears fragment stack and start new fragment
     *
     * @param contentFragment fragment to start
     */
    void startFragment(ContentFragment contentFragment);

    /**
     * Starts new fragment, prev saved in backstack
     *
     * @param contentFragment fragment to start
     */
    void startFragmentWithStacking(ContentFragment contentFragment);

    /**
     * Process back logic. Soft back will not quit activity
     *
     * @param soft if true than caused be menu back pressed. if false - hardware back button was pressed.
     */
    void back(boolean soft);

    /**
     * Set title for current activity
     *
     * @param title title to show
     */
    void setTitle(CharSequence title);

    /**
     * Set title for current activity
     *
     * @param titleId string resource if to set as title
     */
    void setTitle(int titleId);

    /**
     * Toggle left menu on-off if has one in activity
     */
    void toggleMenu();

    /**
     * Enable ot disable left menu in activity if has one
     *
     * @param enabled true to enable menu
     */
    void setMenuEnabled(boolean enabled);

    /**
     * Makes progress visible with no title
     */
    void showProgress();

    /**
     * Hides progress
     */
    void hideProgress();

    /**
     * Show progress with title
     *
     * @param titleId string resource for progress title
     */
    void showProgress(int titleId);

    /**
     * Show progress with title
     *
     * @param title progress title
     */
    void showProgress(String title);

    /**
     * Run in UI thread of activity. For content fragment runnable may not be run if frgamnet is not attached
     *
     * @param runnable to run in UI thread
     */
    void runOnUiThread(Runnable runnable);


    /**
     * Occurs just after fragment changes - here can be updated for ex. title
     */
    void updateUI();
}
