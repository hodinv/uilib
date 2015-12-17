package com.hodinv.uilib.utils;

import android.view.View;

/**
 * Created by Vasily Hodin on 8/21/2015.
 * Adds switching by selection for views
 */
public class TextViewSwitcher {

    /**
     * Create swithcer from views
     *
     * @param views      view to switch
     * @param current    current view position
     * @param onSelected callback on select change
     */
    public static void create(final View[] views, int current, final OnSelected onSelected) {
        for (int i = 0; i < views.length; i++) {
            views[i].setSelected(current == i);
            final int finalI = i;
            views[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!views[finalI].isSelected()) {
                        for (int j = 0; j < views.length; j++) {
                            views[j].setSelected(finalI == j);
                        }
                        onSelected.onItemSelected(finalI);
                    }
                }
            });
        }
    }

    /**
     * Sets selected swicthed view
     *
     * @param views views
     * @param pos   position of selcted
     */
    public static void setSelected(View[] views, int pos) {
        for (int i = 0; i < views.length; i++) {
            views[i].setSelected(pos == i);
        }
    }

    /**
     * Interface for switcher item selction
     */
    public interface OnSelected {
        /**
         * On item pressed (selected)
         *
         * @param position in list of view
         */
        void onItemSelected(int position);
    }
}
