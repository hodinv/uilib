package com.hodinv.uilib.asynctasks;

import android.os.AsyncTask;

/**
 * Created by vhodin on 17.12.2015.
 */
public abstract class ControlledAsyncTask<Progress, Result> extends AsyncTask<Object, Progress, Result> {

    private FutureCallback<Result> mCallback;

    public ControlledAsyncTask(FutureCallback<Result> callback) {
        mCallback = callback;
    }

    AsyncTasksContentFragment mContentFragment;

    private volatile Throwable error;

    void registerFragment(AsyncTasksContentFragment contentFragment) {
        mContentFragment = contentFragment;
    }

    @Override
    protected final void onCancelled() {
        mContentFragment.taskDone(this);
        super.onCancelled();
    }

    protected abstract Result doInBackground();

    @Override
    protected final Result doInBackground(Object... params) {
        try {
            return doInBackground();
        } catch (Exception ex) {
            error = ex;
        }
        return null;
    }

    @Override
    protected final void onPostExecute(Result result) {
        mContentFragment.taskDone(this);
        if(error == null) {
            mCallback.onSuccess(result);
        } else {
            mCallback.onFailure(error);
        }
        super.onPostExecute(result);
    }

}

