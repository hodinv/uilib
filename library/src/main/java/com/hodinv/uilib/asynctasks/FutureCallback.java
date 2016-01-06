package com.hodinv.uilib.asynctasks;

/**
 * Created by vasily on 18.12.15.
 * Interface to do action after got result or failed to get result
 */
public interface FutureCallback<Result> {
    /**
     * Called if result is ready
     *
     * @param result that is got
     */
    void onSuccess(Result result);

    /**
     * Called if result cant be produced
     *
     * @param error that cause not getting result
     */
    void onFailure(Throwable error);
}
