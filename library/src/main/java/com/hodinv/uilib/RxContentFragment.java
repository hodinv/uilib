package com.hodinv.uilib;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Adds handling of rxjava absevables lifecycle
 * Created by vhodin on 17.12.2015.
 */
public class RxContentFragment extends ContentFragment {
    private List<Holder> mCurrentSubscriptions = new CopyOnWriteArrayList<>();
    private List<Holder> mPendingSubscriptions = new CopyOnWriteArrayList<>();

    /**
     * Add subscriptions that will be removed on destroy.
     * Sets threads as well
     *
     * @param subscription mObservable.OnSubscribe to add mAction
     * @param action       mAction ot do (add to subscription)
     * @param <T>          data type passed from subscription to observer
     */
    public <T> void addSubscription(Observable.OnSubscribe<T> subscription, final Observer<? super T> action) {
        addSubscription(Observable.create(subscription), action);
    }

    /**
     * Add subscriptions that will be removed on destroy.
     * Sets threads as well
     *
     * @param observable mObservable to add mAction
     * @param action     mAction ot do (add to mObservable)
     * @param <T>        data type passed from mObservable to observer
     */
    public <T> void addSubscription(Observable<T> observable, final Observer<? super T> action) {

        final Holder<T> holder = new Holder<>();

        holder.mObservable = observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCurrentSubscriptions.remove(holder);
                    }
                }).doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        mCurrentSubscriptions.remove(holder);
                    }
                }).cache();
        holder.mAction = action;
        holder.mSubscription = holder.mObservable.subscribe(action);
        mCurrentSubscriptions.add(holder);

    }

    /**
     * Run mObservable with no cennection to fragment lifecycle. just helper to setup threads
     *
     * @param observable mObservable to run
     */
    protected void runSubscription(Observable<?> observable) {
        observable.subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(mConsume);
    }


    /**
     * Hack for egg-chicken problem
     */
    private final static class Holder<T> {
        Subscription mSubscription;
        Observable<T> mObservable;
        Observer<? super T> mAction;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onStart() {
        super.onStart();
        for (Holder holder : mPendingSubscriptions) {
            holder.mSubscription = holder.mObservable.subscribe(holder.mAction);
        }
        mPendingSubscriptions.clear();
        updateUI();
    }

    @Override
    public void onStop() {
        super.onStop();
        for (Holder holder : mCurrentSubscriptions) {
            if (!holder.mSubscription.isUnsubscribed()) {
                holder.mSubscription.unsubscribe();
                mPendingSubscriptions.add(holder);
            }
        }
        mCurrentSubscriptions.clear();
    }


    private Observer<Object> mConsume = new Observer<Object>() {
        /**
         * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
         * <p/>
         * The {@link Observable} will not call this method if it calls {@link #onError}.
         */
        @Override
        public void onCompleted() {

        }

        /**
         * Notifies the Observer that the {@link Observable} has experienced an error condition.
         * <p/>
         * If the {@link Observable} calls this method, it will not thereafter call {@link #onNext} or
         * {@link #onCompleted}.
         *
         * @param e the exception encountered by the Observable
         */
        @Override
        public void onError(Throwable e) {

        }

        /**
         * Provides the Observer with a new item to observe.
         * <p/>
         * The {@link Observable} may call this method 0 or more times.
         * <p/>
         * The {@code Observable} will not call this method again after it calls either {@link #onCompleted} or
         * {@link #onError}.
         *
         * @param o the item emitted by the Observable
         */
        @Override
        public void onNext(Object o) {

        }
    };
}
