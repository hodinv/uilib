package com.hodinv.uilib;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Vasily Hodin on 13.07.15.
 * Implements progress par logic
 * progressbar id = lyt_progress
 * progressbar title = txt_progress_title
 */
public class ProgressContentHolderActivity extends ContentHolderActivity {


    private View mProgress;
    private TextView mProgressTtitle;


    @Override
    public int getDefaultLayout() {
        return R.layout.activity_progress_content_holder;
    }

    @Override
    public void setupUI() {
        super.setupUI();
        mProgress = findViewById(R.id.lyt_progress);
        mProgressTtitle = (TextView) findViewById(R.id.txt_progress_title);
    }


    @Override
    public void showProgress() {
        if (mProgress != null) {
            mProgress.setVisibility(View.VISIBLE);
        }
        if (mProgressTtitle != null) {
            mProgressTtitle.setVisibility(View.GONE);
        }
    }


    @Override
    protected void goBack(boolean soft) {
        hideProgress();
        super.goBack(soft);
    }

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

    @Override
    public void hideProgress() {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
        }
    }
}
