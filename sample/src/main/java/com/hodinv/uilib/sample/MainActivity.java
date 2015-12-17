package com.hodinv.uilib.sample;

import com.hodinv.uilib.ContentHolderActivity;

public class MainActivity extends ContentHolderActivity {

    @Override
    public void setupUI() {
        super.setupUI();
        startFragment(new FirstFragment());
    }
}
