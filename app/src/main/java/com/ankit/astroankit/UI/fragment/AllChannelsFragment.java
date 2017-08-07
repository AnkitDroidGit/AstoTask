package com.ankit.astroankit.UI.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankit.astroankit.R;
import com.ankit.astroankit.UI.MainActivity;
import com.ankit.astroankit.UI.UIUtils;
import com.ankit.astroankit.UI.adapters.AllChannelsAdapter;
import com.ankit.astroankit.db.DatabaseHelper;
import com.ankit.astroankit.db.DatabaseSingleton;
import com.ankit.astroankit.models.ChannelListResponse;
import com.ankit.astroankit.models.ChannelModel;
import com.ankit.astroankit.network.ApiUtils;
import com.ankit.astroankit.network.IAPIService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class AllChannelsFragment extends Fragment implements AllChannelsAdapter.OnItemClickListener, View.OnClickListener {
    Context mContext;
    IAPIService iApiService;
    AllChannelsAdapter allChannelsAdapter;
    @BindView(R.id.channel_recycler)
    RecyclerView channelRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.sort_name)
    TextView sortByName;
    @BindView(R.id.sort_number)
    TextView sortByNumber;
    @BindView(R.id.sort_layout)
    LinearLayout sortLayout;
    private boolean isShow;
    private List<ChannelModel> channelModelList;

    String title = "";
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
        setRetainInstance(true);
        ((MainActivity) mContext).showProgressDialog();
        if (getArguments() != null)
            title = getArguments().getString("title");
        databaseHelper = DatabaseSingleton.getInstance().getHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_channels, container, false);
        ButterKnife.bind(this, view);

        fab.setOnClickListener(this);
        sortByName.setOnClickListener(this);
        sortByNumber.setOnClickListener(this);

        channelRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        channelRecyclerView.setLayoutManager(layoutManager);
        channelModelList = new ArrayList<>();
        allChannelsAdapter = new AllChannelsAdapter(channelModelList, this);
        channelRecyclerView.setAdapter(allChannelsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(channelRecyclerView.getContext(),
                layoutManager.getOrientation());
        channelRecyclerView.addItemDecoration(dividerItemDecoration);
        if (title.contains("Favourite"))
            loadFavouriteList();
        else
            loadChannelList();
        return view;
    }

    private void loadFavouriteList() {
        allChannelsAdapter.clearItems();
        allChannelsAdapter.updateChannels(databaseHelper.getFavouriteChannelModels());
        ((MainActivity) mContext).hideProgressDialog();
    }

    @Override
    public void onResume() {
        ((MainActivity) mContext).setToolbarTitle(title);
        ((MainActivity) mContext).setHomeAsEnabled(true);
        ((MainActivity) mContext).manageDrawerLayout(true);
        ((MainActivity) mContext).setToolbarColor(getResources().getColor(R.color.colorPrimary));
        super.onResume();
    }

    public void loadChannelList() {
        iApiService = ApiUtils.getApiService();
        iApiService.getChannelList().enqueue(new Callback<ChannelListResponse>() {
            @Override
            public void onResponse(Call<ChannelListResponse> call, Response<ChannelListResponse> response) {
                ((MainActivity) mContext).hideProgressDialog();
                if (response.isSuccessful()) {
                    allChannelsAdapter.clearItems();
                    allChannelsAdapter.updateChannels(response.body().getChannelList());
                    Log.d("MainActivity", "posts loaded from API");
                    databaseHelper.saveAllChannels(response.body().getChannelList());
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ChannelListResponse> call, Throwable t) {
                ((MainActivity) mContext).showSnackBar("Error");
                ((MainActivity) mContext).hideProgressDialog();
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    @Override
    public void onItemClick(ChannelModel item) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (isShow) {
                    hideLayout();
                } else
                    showLayout();
                break;
            case R.id.sort_name:
                allChannelsAdapter.sortByName();
                hideLayout();
                break;
            case R.id.sort_number:
                allChannelsAdapter.sortByNumber();
                hideLayout();
                break;
        }
    }

    private void hideLayout() {
        sortLayout.animate()
                .translationY(sortLayout.getHeight() - UIUtils.convertDpToPixel(80, mContext))
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        sortLayout.setVisibility(View.GONE);
                        isShow = false;

                        final OvershootInterpolator interpolator = new OvershootInterpolator();
                        ViewCompat.animate(fab).
                                rotation(0f).
                                withLayer().
                                setDuration(300).
                                setInterpolator(interpolator).
                                start();

                    }
                });

    }

    private void showLayout() {
        final OvershootInterpolator interpolator = new OvershootInterpolator();
        ViewCompat.animate(fab).
                rotation(135f).
                withLayer().
                setDuration(300).
                setInterpolator(interpolator).
                start();

        isShow = true;
        // Prepare the View for the animation
        sortLayout.setVisibility(View.VISIBLE);
        sortLayout.setAlpha(0.0f);

        // Start the animation
        sortLayout.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(null);
    }
}