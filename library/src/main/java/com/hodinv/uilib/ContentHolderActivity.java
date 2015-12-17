package com.hodinv.uilib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Base activity that holds ContentFragment
 * Created by vhodin on 17.12.2015.
 */
public class ContentHolderActivity extends AppCompatActivity {

    private View mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
    }

    /**
     * Return base layout for activity
     * Layout should contain resurce iten with id=content
     *
     * @return laout resurce id
     */
    public int getDefaultLayout() {
        return R.layout.activity_content_holder;
    }

    /**
     * Setup UI in OnCreate
     */
    public void setupUI() {
        setContentView(getDefaultLayout());
        mContent = findViewById(R.id.content);
        if (mContent == null) {
            throw new RuntimeException("Element with id=content not found");
        }
    }

    /**
     * Clears fragment stack and start new fragemtn
     *
     * @param contentFragment
     */
    public void startFragment(ContentFragment contentFragment) {
        // TODO:
    }

    /**
     * Starts new fragment, prev svaed in backstack
     *
     * @param contentFragment
     */
    public void startFragmentWithStacking(ContentFragment contentFragment) {
        // TODO:
    }


    /**
     * Return current top fragment
     *
     * @return current fragment or null
     */
    public ContentFragment getCurrentFragment() {
        // TODO:
        return null;
    }

    /**
     * Process back logic
     *
     * @param soft if true than caused be menu back pressed. if false - hardware back button was pressed
     */
    public void back(boolean soft) {
        // TODO:
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        back(false);
    }
}
