package com.hodinv.uilib.sample;

import android.util.Log;

import com.hodinv.uilib.ContentHolderActivity;
import com.hodinv.uilib.LeftMenuContentHolderActivity;
import com.hodinv.uilib.ProgressContentHolderActivity;
import com.hodinv.uilib.ToolbarContentHolderActivity;
import com.hodinv.uilib.utils.ResourcesHelper;

public class MainActivity extends LeftMenuContentHolderActivity {

    @Override
    public void setupUI() {
        super.setupUI();
        startFragment(new FirstFragment());
    }

    @Override
    public int getMenuLayout() {
        return R.layout.menu_main;
    }

    @Override
    public int[] getMenuIds() {
        return ResourcesHelper.loadResourcesIds(R.id.class, new ResourcesHelper.Filter() {
            @Override
            public boolean match(String name) {
                return name.startsWith("menu_");
            }
        });
    }

    @Override
    protected void onLeftMenuSelected(int menuId) {
        switch (menuId) {
            case R.id.menu_first:
                startFragment(new FirstFragment());
                break;
            case R.id.menu_second:
                startFragment(new SecondFragment());
                break;
        }
    }
}


