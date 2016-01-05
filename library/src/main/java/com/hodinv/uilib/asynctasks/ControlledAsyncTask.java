package com.hodinv.uilib.asynctasks;

import android.os.AsyncTask;

/**
 * Created by vhodin on 17.12.2015.
 * AsyncTask that can be processed in AsyncTasksContentFragment
 */
public abstract class ControlledAsyncTask<Progress, Result> extends AsyncTask<Object, Progress, Result> {

    private FutureCallback<Result> mCallback;

    public ControlledAsyncTask(FutureCallback<Result> callback) {
        mCallback = callback;
    }

    AsyncTasksContentFragment mContentFragment;

    private volatile Throwable error;

    /**
     * Called from frgamne to register holder
     *
     * @param contentFragment that holds this task
     */
    void registerFragment(AsyncTasksContentFragment contentFragment) {
        mContentFragment = contentFragment;
    }

    @Override
    protected final void onCancelled() {
        mContentFragment.taskDone(this);
        super.onCancelled();
    }

    /**
     * Main method that produces result. If throws exception this exception will be passed to future
     * @return produced result
     */
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
        if (error == null) {
            mCallback.onSuccess(result);
        } else {
            mCallback.onFailure(error);
        }
        super.onPostExecute(result);
    }

}

