package com.hodinv.uilib.asynctasks;

import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.hodinv.uilib.ContentFragment;
import com.hodinv.uilib.ContentFragmentHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * Content fragment conectied with ControlledAsycTask to manage lifecycle (cancel in onStop)
 * Created by vhodin on 17.12.2015.
 */
public class AsyncTasksContentFragment extends ContentFragment {
    @SuppressWarnings("rawtypes")
    private List<ControlledAsyncTask> taskToCancel = new LinkedList<>();


    /**
     * Starts task and adds it to lasks list. Also pass self to task as holder
     *
     * @param taskToStart task that should be started
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void startTask(final ControlledAsyncTask taskToStart) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                // check if not in UI thread
                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    synchronized (AsyncTasksContentFragment.this) {
                        taskToCancel.add(taskToStart);
                        taskToStart.registerFragment(AsyncTasksContentFragment.this);
                        taskToStart.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                } else {
                    holder.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startTask(taskToStart);
                        }
                    });
                }
            }
        });

    }

    @SuppressWarnings("rawtypes")
    synchronized void taskDone(ControlledAsyncTask task) {
        taskToCancel.remove(task);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void onStop() {
        super.onStop();
        synchronized (this) {
            for (ControlledAsyncTask task : taskToCancel) {
                task.cancel(true);
            }
            taskToCancel.clear();
        }
    }
}
