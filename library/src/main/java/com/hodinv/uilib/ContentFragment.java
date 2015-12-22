package com.hodinv.uilib;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hodinv.uilib.utils.KeyboardHelper;

import java.lang.ref.WeakReference;

/**
 * Base class for fragment that is holde by ContentHolderActivity
 * Created by vhodin on 17.12.2015.
 */
public class ContentFragment extends Fragment implements ContentFragmentHolder {


    private WeakReference<ContentFragmentHolder> mHolderRef = new WeakReference<ContentFragmentHolder>(null);


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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof ContentFragmentHolder) {
            mHolderRef = new WeakReference<ContentFragmentHolder>((ContentFragmentHolder) activity);
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
     * @param action
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
     * @param action
     * @param actionIfNoActivity
     */
    protected void checkRef(IfHasActivity action, Runnable actionIfNoActivity) {
        ContentFragmentHolder holder = mHolderRef.get();
        if (holder != null) {
            action.doAction(holder);
        }
    }


    protected interface IfHasActivity {
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
     * @return
     */
    public int getMenuId() {
        return 0;
    }

    @Override
    public void startFragment(final ContentFragment contentFragment) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.startFragment(contentFragment);
            }
        });
    }

    @Override
    public void startFragmentWithStacking(final ContentFragment contentFragment) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.startFragmentWithStacking(contentFragment);
            }
        });
    }

    public boolean hasLeftMenu() {
        return true;
    }


    @Override
    public void setTitle(final CharSequence title) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.setTitle(title);
            }
        });
    }

    @Override
    public void setTitle(final int titleId) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.setTitle(titleId);
            }
        });
    }

    @Override
    public void toggleMenu() {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.toggleMenu();
            }
        });
    }

    @Override
    public void setMenuEnabled(final boolean enabled) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.setMenuEnabled(enabled);
            }
        });
    }

    @Override
    public void showProgress() {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.showProgress();
            }
        });
    }

    @Override
    public void hideProgress() {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.hideProgress();
            }
        });

    }

    @Override
    public void showProgress(final int titleId) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.showProgress(titleId);
            }
        });

    }

    @Override
    public void showProgress(final String title) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.showProgress(title);
            }
        });
    }

    @Override
    public void runOnUiThread(final Runnable runnable) {
        checkRef(new IfHasActivity() {
            @Override
            public void doAction(@NonNull ContentFragmentHolder holder) {
                holder.runOnUiThread(runnable);
            }
        });
    }

}
