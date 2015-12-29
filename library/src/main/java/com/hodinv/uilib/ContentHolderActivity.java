package com.hodinv.uilib;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity that holds ContentFragment
 * Created by vhodin on 17.12.2015.
 */
public class ContentHolderActivity extends AppCompatActivity implements ContentFragmentHolder {

    /**
     * Return base layout for activity
     * Layout should contain resurce iten with id=lyt_content
     *
     * @return laout resource id
     */
    public int getDefaultLayout() {
        return R.layout.activity_content_holder;
    }

    /**
     * Setup UI in OnCreate
     */
    public void setupUI() {
        setContentView(getDefaultLayout());
        if (findViewById(R.id.lyt_content) == null) {
            throw new RuntimeException("Element with id=lyt_content not found");
        }
    }


    /**
     * Process back logic. Soft back will not quit activity
     *
     * @param soft if true than caused be menu back pressed. if false - hardware back button was pressed.
     */
    @Override
    public void back(boolean soft) {
        ContentFragment fragment = getCurrentFragment();
        if (fragment != null) {
            if (getCurrentFragment().onBack(soft)) {
                return;
            }
        }
        goBack(soft);

    }

    /**
     * Check if we can exit without confirmation. If activity requies confirmation to exit - override it
     *
     * @return true if confirmation os not needed
     */
    @UiThread
    protected boolean canExitImmediate() {
        return true;
    }


    /**
     * Ask for confirmation of exit. If confirmation needed - it can be done here
     *
     * @param exitAction do it if confirmed (here stanmdart exit code of activity. Should be run in UI thread
     */
    @UiThread
    protected void confirmExit(Runnable exitAction) {
        exitAction.run();
    }


    /**
     * Will be triggered just before exit
     * Here can be applied some exit-time logic
     */
    @UiThread
    protected void onExit() {
        // do nothing
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
    }


    /**
     * Clears fragment stack and start new fragment
     *
     * @param contentFragment fragment to start
     */
    @Override
    public void startFragment(ContentFragment contentFragment) {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lyt_content, contentFragment);
        transaction.commit();
    }

    /**
     * Starts new fragment, prev saved in backstack
     *
     * @param contentFragment fragment to start
     */
    @Override
    public void startFragmentWithStacking(ContentFragment contentFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.lyt_content, contentFragment).addToBackStack(null);
        transaction.commit();
    }


    /**
     * Return current top fragment
     *
     * @return current fragment or null
     */
    public ContentFragment getCurrentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentById(R.id.lyt_content);
    }


    /**
     * In base fragment holder this methods do nothing - they are for more complex activities
     */


    @Override
    public void toggleMenu() {
        // do nothing
    }

    @Override
    public void setMenuEnabled(boolean enabled) {
        // do nothing
    }

    @Override
    public void showProgress() {
        // Do nothing
    }

    @Override
    public void hideProgress() {
        // Do nothing
    }

    @Override
    public void showProgress(int titleId) {
        // Do nothing
    }

    @Override
    public void showProgress(String title) {
        // Do nothing
    }

    /**
     * Perform standard back logic with confirm
     *
     * @param soft if true than caused be menu back pressed. if false - hardware back button was pressed.
     */
    protected void goBack(boolean soft) {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            // above to quit - only if it is not soft
            if (!soft) {
                if (canExitImmediate()) {
                    mOnExit.run();
                } else {
                    confirmExit(mOnExit);
                }
            }
        } else {
            // force UI update becase fragment just arrived from backstack
            updateUI();
        }
    }

    /**
     * Occurs just after fragment changes - here can be updated for ex. title
     */
    @Override
    public void updateUI() {
        ContentFragment contentFragment = getCurrentFragment();
        if (contentFragment != null) {
            contentFragment.updateUI();
        }
    }

    @Override
    public void onBackPressed() {
        back(false);
    }

    /**
     * Do finish this activity without asking with onExit called
     */
    protected final void immediateFinish() {
        onExit();
        supportFinishAfterTransition();
    }


    private final Runnable mOnExit = new Runnable() {
        @Override
        public void run() {
            immediateFinish();
        }
    };


}
