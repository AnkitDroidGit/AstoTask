package com.ankit.astroankit.UI.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankit.astroankit.R;
import com.ankit.astroankit.UI.MainActivity;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class NavigationFragment extends Fragment implements View.OnClickListener {
    Context mContext;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        setUpDrawer(view);
        return view;
    }

    private void setUpDrawer(View view) {
        TextView favouriteChannels = (TextView) view.findViewById(R.id.favourite_menu);
        TextView allChannels = (TextView) view.findViewById(R.id.all_channels_menu);
        TextView tvGuide = (TextView) view.findViewById(R.id.tv_guide_menu);

        favouriteChannels.setOnClickListener(this);
        allChannels.setOnClickListener(this);
        tvGuide.setOnClickListener(this);
    }

    public ActionBarDrawerToggle setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackpressed();
                //closeSoftKeyboard(getActivity(), getView());
                ((MainActivity) getActivity()).hideKeypad(getActivity().getCurrentFocus());
            }
        });
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        return mDrawerToggle;
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }


    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favourite_menu: {
                AllChannelsFragment allChannelsFragment = new AllChannelsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", "Favourite Channels");
                allChannelsFragment.setArguments(bundle);
                ((MainActivity) mContext).clearFragmentStack();
                ((MainActivity) mContext).addFragment(allChannelsFragment);
            }
            break;
            case R.id.all_channels_menu: {
                AllChannelsFragment allChannelsFragment = new AllChannelsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", "All Channels");
                allChannelsFragment.setArguments(bundle);
                ((MainActivity) mContext).clearFragmentStack();
                ((MainActivity) mContext).addFragment(allChannelsFragment);
            }
            break;
            case R.id.tv_guide_menu:
                ((MainActivity) mContext).clearFragmentStack();
                ((MainActivity) mContext).addFragment(new TVGuideFragment());
                break;
        }
        closeDrawer();
    }

    private void closeDrawer() {
        if (mDrawerLayout != null) {
            if (mFragmentContainerView != null) {
                mDrawerLayout.closeDrawers();
//                txtNotificationSettings.setVisibility(View.GONE);
            }

        }
    }

//    /**
//     * Callbacks interface that all activities using this fragment must implement.
//     */
//    public interface NavigationDrawerCallbacks {
//        /**
//         * Called when an item in the navigation drawer is selected.
//         */
//        void onNavigationDrawerItemSelected(int position);
//    }
}
