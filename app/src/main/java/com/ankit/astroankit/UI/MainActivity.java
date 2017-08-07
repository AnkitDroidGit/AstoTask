package com.ankit.astroankit.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ankit.astroankit.Constants;
import com.ankit.astroankit.R;
import com.ankit.astroankit.UI.fragment.AllChannelsFragment;
import com.ankit.astroankit.UI.fragment.NavigationFragment;
import com.ankit.astroankit.db.DatabaseHelper;
import com.ankit.astroankit.db.DatabaseSingleton;

import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    public Stack<Fragment> mFragmentStack = null;
    public DatabaseHelper databaseHelper;
    android.support.v4.app.FragmentManager mFragmentManager;
    Context mContext;
    private boolean doubleBackToExitPressedOnce;
    private View progressBarLayout;
    private ViewGroup mParentView;

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerLinear;
    private ActionBarDrawerToggle mActionbarToggle;
    private Toolbar mToolbar;
    public ActionBar ab;


    interface BackKeyListener {
        boolean handleBackKeyPress();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        databaseHelper = DatabaseSingleton.getInstance().getHelper();
        mFragmentManager = getSupportFragmentManager();
        initToolbar();
        AllChannelsFragment allChannelsFragment = new AllChannelsFragment();
        Bundle bundle = new Bundle();
        if (databaseHelper.getFavouriteChannelIds().size() >= 1) {
            bundle.putString("title", "Favourite Channels");
        } else {
            bundle.putString("title", "All Channels");
        }
        allChannelsFragment.setArguments(bundle);
        replaceFragment(allChannelsFragment);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerLinear)) {
            mDrawerLayout.closeDrawer(mDrawerLinear);
        } else {
            handleButtonBarBackKey();
        }
    }

    private void handleButtonBarBackKey() {
        List<Fragment> frags = getSupportFragmentManager().getFragments();
        Fragment lastFrag = getLastNotNull(frags);
        if (!(lastFrag instanceof BackKeyListener)) {
            onBackpressed();
        } else {
            if (!((BackKeyListener) lastFrag).handleBackKeyPress())
                onBackpressed();
        }
    }


    private Fragment getLastNotNull(List<Fragment> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            Fragment frag = list.get(i);
            if (frag != null) {
                return frag;
            }
        }
        return null;
    }

    /**
     * Initializes drawer items
     */

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        mDrawerLinear = (RelativeLayout) findViewById(R.id.left_drawer);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        NavigationFragment mNavigationDrawerFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mActionbarToggle = mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout, mToolbar);
        mActionbarToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mActionbarToggle.isDrawerIndicatorEnabled()) {
                    onBackPressed();
                }
            }
        });
    }

    /**
     * Initializes toolbar
     */
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false);
        mToolbar.setContentInsetsAbsolute(0, 0);
        mToolbar.setDrawingCacheBackgroundColor(mContext.getResources().getColor(R.color.white));
        // setToolbarTitleColor(R.color.white);
        setToolbarColor(getResources().getColor(R.color.colorPrimary));
        //  ab.setHomeAsUpIndicator(R.drawable.ic_back);
        initDrawer();
    }

    /**
     * Changes toolbar color and manages drop down shadow
     *
     * @param color required color
     */
    public void setToolbarColor(int color) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }

    /**
     * Changes toolbar title
     *
     * @param title required title
     */
    public void setToolbarTitle(String title) {
        //  Preferences.setPreference(mContext, "h", title);
        mToolbar.setTitle(title);
    }

    public String getToolbarTitle() {
        return mToolbar.getTitle().toString();
    }

    /**
     * /**
     * Manages the berger icon(to show berger menu or back icon)
     *
     * @param status status
     */
    public void setHomeAsEnabled(boolean status) {
        mActionbarToggle.setDrawerIndicatorEnabled(status);
    }

    /**
     * Lock or Unlocks the drawer layout
     *
     * @param status status
     */
    public void manageDrawerLayout(boolean status) {
        if (mDrawerLayout != null) {
            if (status) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            } else {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

        }
    }


    public void saveToPreferences(String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getFromPreferences(String key) {
        final SharedPreferences prefs = getSharedPreferences(Constants.PREF_FILE_CONFIG, Context.MODE_PRIVATE);
        String value = prefs.getString(key, "");
        if (!value.isEmpty()) {
            return value;
        }
        return "";
    }

    public void showProgressDialog() {
        mParentView = (ViewGroup) findViewById(android.R.id.content);
        if (mParentView != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            progressBarLayout = inflater.inflate(R.layout.simple_progressbar, mParentView, false);
            mParentView.addView(progressBarLayout);
            if (progressBarLayout != null) {
                progressBarLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideProgressDialog() {
        if (progressBarLayout != null) {
            if (mParentView != null) {
                mParentView.removeView(progressBarLayout);
            }
            progressBarLayout.setVisibility(View.GONE);
        }
    }

    public void showSnackBar(String message) {
        Snackbar sb = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = sb.getView();
        sbView.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(mContext.getResources().getColor(R.color.white));
        sb.show();
    }


    public void onBackpressed() {
        if (mFragmentStack.size() >= 2) {
            showLastFragment();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please tap BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void getLastFragment() {
        if (mFragmentStack.size() > 1) {
            for (int i = 0; i < mFragmentStack.size(); i++) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                mFragmentStack.lastElement().onResume();
                ft.show(mFragmentStack.lastElement());
                ft.commitAllowingStateLoss();
            }
        }
        //  return mFragmentStack.lastElement();
    }

    public void replaceFragment(Fragment fragment) {
        if (mFragmentStack != null) {
            Log.e("Size", mFragmentStack.size() + "");
        }
        mFragmentStack = new Stack<>();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.layout_frame_content, fragment);
        mFragmentStack.push(fragment);
        transaction.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment) {
        if (mFragmentManager != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.add(R.id.layout_frame_content, fragment);
            mFragmentStack.lastElement().onPause();
            transaction.hide(mFragmentStack.lastElement());
            mFragmentStack.push(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public void clearFragmentStack() {
        if (mFragmentStack.size() >= 2) {
            //   Log.i("remove frag" + i, " " + mFragmentStack.size());
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            mFragmentStack.lastElement().onPause();
            ft.remove(mFragmentStack.pop());
            mFragmentStack.lastElement().onResume();
            ft.show(mFragmentStack.lastElement());
            ft.commitAllowingStateLoss();
        }
    }

    public void showLastFragment() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            hideKeypad(this.getCurrentFocus());
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        mFragmentStack.lastElement().onPause();
        ft.remove(mFragmentStack.pop());
        mFragmentStack.lastElement().onResume();
        ft.show(mFragmentStack.lastElement());
        ft.commitAllowingStateLoss();
    }

    public void hideKeypad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
