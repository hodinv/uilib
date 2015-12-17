package com.hodinv.uilib.sample;

import com.hodinv.uilib.ContentHolderActivity;
import com.hodinv.uilib.ProgressContentHolderActivity;
import com.hodinv.uilib.ToolbarContentHolderActivity;

public class MainActivity extends ToolbarContentHolderActivity {

    @Override
    public void setupUI() {
        super.setupUI();
        startFragment(new FirstFragment());
    }
}
