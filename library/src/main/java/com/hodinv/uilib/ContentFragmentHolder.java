package com.hodinv.uilib;

/**
 * Created by vhodin on 17.12.2015.
 */
public interface ContentFragmentHolder {
    void startFragment(ContentFragment contentFragment);

    void startFragmentWithStacking(ContentFragment contentFragment);

    void back(boolean soft);

    void setTitle(CharSequence title);

    void setTitle(int titleId);

    void toggleMenu();

    void setMenuEnabled(boolean enabled);

    void showProgress();

    void hideProgress();

    void showProgress(int titleId);

    void showProgress(String title);

    void runOnUiThread(Runnable runnable);

    void updateUI();
}
