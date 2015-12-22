package com.hodinv.uilib.sample;

import com.hodinv.uilib.utils.EnumsHelper;

/**
 * Created by vhodin on 22.12.2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EnumsHelper.init(this);
        EnumsHelper.getInstance().loadEnum(Titles.class);
    }
}
