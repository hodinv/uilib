package com.hodinv.uilib;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Base class for fragment that is holde by ContentHolderActivity
 * Created by vhodin on 17.12.2015.
 */
public class ContentFragment extends Fragment implements ContentFragmentHolder {


    private WeakReference<ContentFragmentHolder> mHolderRef = new WeakReference<>(null);


    /**
     * Go back in holding activity
     *
     * @param soft if it is soft back
     */
    public void back(final boolean soft) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.back(soft);
            }
        });
    }


    /**
     * If holder has toolbar (instance of ToolbarContentHolderActivity)
     *
     * @return true if toolbar should be visible
     */
    public boolean showToolbar() {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    /**
     * Process back logic
     *
     * @param soft if back was pressed in menu
     * @return true if back is processed and consumed
     */
    public boolean onBack(boolean soft) {
        return false;
    }


    /**
     * Here base UI logic. Currently - changin of activity title
     */
    @Override
    public void updateUI() {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                String title = getTitle();
                if (title != null) {
                    holder.setTitle(title);
                } else {
                    int titleId = getDefaultTitle();
                    if (titleId != 0) {
                        holder.setTitle(titleId);
                    }
                }
            }
        });

    }


    /**
     * Activity with left menu check this function to decide what to show - menu ot back arrow
     *
     * @return true if fragment needs left menu, false if it is child fragment and handles onle soft back logic
     */
    public boolean hasLeftMenu() {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof ContentFragmentHolder) {
            mHolderRef = new WeakReference<>((ContentFragmentHolder) activity);
            ((ContentFragmentHolder) activity).updateUI();
        } else {
            throw new RuntimeException("ContentFragment can be used only in ContentHolderActivity self or inherited classes");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHolderRef.clear();
    }

    /**
     * Performs action only if fragment has ref to activity
     *
     * @param action holder to run action if attached to activity
     */
    protected void checkRef(IfHasActivity action) {
        ContentFragmentHolder holder = mHolderRef.get();
        if (holder != null) {
            action.doAction(holder);
        }
    }

    /**
     * Performs action only if fragment has ref to activity
     *
     * @param action             holder to run action if attached to activity
     * @param actionIfNoActivity runnable to run if not attached to activity
     */
    protected void checkRef(IfHasActivity action, Runnable actionIfNoActivity) {
        ContentFragmentHolder holder = mHolderRef.get();
        if (holder != null) {
            action.doAction(holder);
        }
    }

    /**
     * Interace for running action if has attached activity
     */
    protected interface IfHasActivity {
        /**
         * Action to preform
         *
         * @param holder activity that holds current fragment
         */
        void doAction(@NonNull ContentFragmentHolder holder);
    }


    /**
     * Direct calls to holder
     */

    /**
     * Activity title to setup. Has more prioruty to getDefaultTitle()
     *
     * @return title to set or null if better use default title
     */
    public String getTitle() {
        return null;
    }

    /**
     * Set activity title when fragment appears (first or from back stack)
     *
     * @return resurse for title. if 0 - title will be not changed
     */
    public int getDefaultTitle() {
        return 0;
    }


    /**
     * Return id of left menu to show as selected (as current)
     *
     * @return menu id to show selected or 0 for no selected menu
     */
    public int getMenuId() {
        return 0;
    }

    /**
     * Clears fragment stack and start new fragment
     *
     * @param contentFragment fragment to start
     */
    @Override
    public void startFragment(final ContentFragment contentFragment) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.startFragment(contentFragment);
            }
        });
    }

    /**
     * Starts new fragment, prev saved in backstack
     *
     * @param contentFragment fragment to start
     */
    @Override
    public void startFragmentWithStacking(final ContentFragment contentFragment) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.startFragmentWithStacking(contentFragment);
            }
        });
    }

    /**
     * Set title for current activity
     *
     * @param title title to show
     */
    @Override
    public void setTitle(final CharSequence title) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.setTitle(title);
            }
        });
    }

    /**
     * Set title for current activity
     *
     * @param titleId string resource if to set as title
     */
    @Override
    public void setTitle(final int titleId) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.setTitle(titleId);
            }
        });
    }

    /**
     * Toggle left menu on-off if has one in activity
     */
    @Override
    public void toggleMenu() {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.toggleMenu();
            }
        });
    }

    /**
     * Enable ot disable left menu in activity if has one
     *
     * @param enabled true to enable menu
     */
    @Override
    public void setMenuEnabled(final boolean enabled) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.setMenuEnabled(enabled);
            }
        });
    }

    /**
     * Makes progress visible with no title
     */
    @Override
    public void showProgress() {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.showProgress();
            }
        });
    }

    /**
     * Hides progress
     */
    @Override
    public void hideProgress() {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.hideProgress();
            }
        });

    }

    /**
     * Show progress with title
     *
     * @param titleId string resource for progress title
     */
    @Override
    public void showProgress(final int titleId) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.showProgress(titleId);
            }
        });

    }

    /**
     * Show progress with title
     *
     * @param title progress title
     */
    @Override
    public void showProgress(final String title) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.showProgress(title);
            }
        });
    }

    /**
     * Run in UI thread of activity. For content fragment runnable may not be run if frgamnet is not attached
     *
     * @param runnable to run in UI thread
     */
    @Override
    public void runOnUiThread(final Runnable runnable) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.runOnUiThread(runnable);
            }
        });
    }


    /**
     * Vreates builder for current fragemnt to help with setting arguments
     *
     * @return builder for arguments
     */
    public ArgumentsBuilder arguments() {
        return new ArgumentsBuilder(this);
    }


    /**
     * Builder for arguments
     */
    public static class ArgumentsBuilder {
        private final Bundle mBundle;
        private ContentFragment mContentFragment;

        /**
         * Creates builder for fragment arguments
         *
         * @param contentFragment fragment to set arguments for
         */
        public ArgumentsBuilder(ContentFragment contentFragment) {
            mContentFragment = contentFragment;
            mBundle = new Bundle();
        }

        public ArgumentsBuilder putString(String key, String value) {
            mBundle.putString(key, value);
            return this;
        }

        public ArgumentsBuilder putInt(String key, int value) {
            mBundle.putInt(key, value);
            return this;
        }

        public ArgumentsBuilder putLong(String key, long value) {
            mBundle.putLong(key, value);
            return this;
        }


        public ArgumentsBuilder putBoolean(String key, boolean value) {
            mBundle.putBoolean(key, value);
            return this;
        }

        public ArgumentsBuilder putFloat(String key, float value) {
            mBundle.putFloat(key, value);
            return this;
        }

        public ArgumentsBuilder putDouble(String key, double value) {
            mBundle.putDouble(key, value);
            return this;
        }

        /**
         * Creates arguemnts bundle and set as arguemnts for fragment
         *
         * @return ready to start fragment with arguments already set
         */
        public ContentFragment build() {
            mContentFragment.setArguments(mBundle);
            return mContentFragment;
        }

    }

}
