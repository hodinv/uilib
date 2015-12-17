// Developed by Softeq Development Corporation
// http://www.softeq.com

package com.hodinv.uilib.asynctasks;

/**
 * Created by vasily on 18.12.15.
 */
public interface FutureCallback<Result> {
    void onSuccess(Result result);

    void onFailure(Throwable error);
}
