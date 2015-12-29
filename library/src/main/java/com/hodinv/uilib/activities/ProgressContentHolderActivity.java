package com.hodinv.uilib.activities;

import android.view.View;
import android.widget.TextView;

import com.hodinv.uilib.ContentHolderActivity;
import com.hodinv.uilib.R;

/**
 * Created by Vasily Hodin on 13.07.15.
 * Implements progress par logic
 * progressbar id = lyt_progress
 * progressbar title = txt_progress_title
 */
public class ProgressContentHolderActivity extends ContentHolderActivity {


    private View mProgress;
    private TextView mProgressTtitle;

    /**
     * Return base layout for activity
     * Layout should contain resurce iten with id=lyt_content
     *
     * @return laout resource id
     */
    @Override
    public int getDefaultLayout() {
        return R.layout.activity_progress_content_holder;
    }

    /**
     * Setup UI in OnCreate
     */
    @Override
    public void setupUI() {
        super.setupUI();
        mProgress = findViewById(R.id.lyt_progress);
        mProgressTtitle = (TextView) findViewById(R.id.txt_progress_title);
    }


    /**
     * Makes progress visible with no title
     */
    @Override
    public void showProgress() {
        if (mProgress != null) {
            mProgress.setVisibility(View.VISIBLE);
        }
        if (mProgressTtitle != null) {
            mProgressTtitle.setVisibility(View.GONE);
        }
    }

    /**
     * Perform standard back logic with confirm. Here also hides progress
     *
     * @param soft if true than caused be menu back pressed. if false - hardware back button was pressed.
     */
    @Override
    protected void goBack(boolean soft) {
        hideProgress();
        super.goBack(soft);
    }

    /**
     * Show progress with title
     * @param titleId string resource for progress title
     */
    @Override
    public void showProgress(int titleId) {
        if (mProgress != null) {
            mProgress.setVisibility(View.VISIBLE);
        }
        if (mProgressTtitle != null) {
            mProgressTtitle.setVisibility(View.VISIBLE);
            mProgressTtitle.setText(titleId);
        }

    }

    /**
     * Show progress with title
     *
     * @param title progress title
     */
    @Override
    public void showProgress(String title) {
        if (mProgress != null) {
            mProgress.setVisibility(View.VISIBLE);
        }
        if (mProgressTtitle != null) {
            mProgressTtitle.setVisibility(View.VISIBLE);
            mProgressTtitle.setText(title);
        }
    }


    /**
     * Hides progress
     */
    @Override
    public void hideProgress() {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
        }
    }
}
