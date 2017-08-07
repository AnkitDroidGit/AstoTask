package com.ankit.astroankit.UI.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankit.astroankit.R;
import com.ankit.astroankit.UI.MainActivity;
import com.ankit.astroankit.UI.adapters.TVGuideAdapter;
import com.ankit.astroankit.db.DatabaseHelper;
import com.ankit.astroankit.db.DatabaseSingleton;
import com.ankit.astroankit.models.ChannelEvent;
import com.ankit.astroankit.models.ChannelModel;
import com.ankit.astroankit.models.EventListResponse;
import com.ankit.astroankit.network.ApiUtils;
import com.ankit.astroankit.network.IAPIService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class TVGuideFragment extends Fragment implements View.OnClickListener {
    Context mContext;
    IAPIService iApiService;
    TVGuideAdapter tvGuideAdapter;
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
    private List<ChannelEvent> channelEventList;
    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
        setRetainInstance(true);
        ((MainActivity) mContext).showProgressDialog();
        iApiService = ApiUtils.getApiService();
        databaseHelper = DatabaseSingleton.getInstance().getHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_guide, container, false);
        ButterKnife.bind(this, view);

        fab.setOnClickListener(this);
        sortByName.setOnClickListener(this);
        sortByNumber.setOnClickListener(this);

        channelRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        channelRecyclerView.setLayoutManager(layoutManager);
        channelEventList = new ArrayList<>();
        tvGuideAdapter = new TVGuideAdapter(channelEventList);
        channelRecyclerView.setAdapter(tvGuideAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(channelRecyclerView.getContext(),
                layoutManager.getOrientation());
        channelRecyclerView.addItemDecoration(dividerItemDecoration);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
        String formattedDate = df.format(c.getTime());
        String startTime = formattedDate + "00:00";
        String endTime = formattedDate + "23:59";
        loadTVGuideData(startTime, endTime, 0);
        return view;
    }

    private void loadTVGuideData(String periodStart, String periodEnd, final int limit) {
        String channelId = "";
        for (ChannelModel channelModel : databaseHelper.get10Channels(limit)) {
            channelId = channelId + "," + channelModel.getChannelId();
        }
        if (channelId.startsWith(","))
            channelId = channelId.substring(1, channelId.length());
        if (channelId.endsWith(","))
            channelId = channelId.substring(0, channelId.length() - 1);
        Log.d("IDs", channelId);
        Log.d("Start", periodStart);
        Log.d("End", periodEnd);

        iApiService.getEvents(channelId, periodStart, periodEnd).enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                if (limit == 0)
                    tvGuideAdapter.clearItems();
                List<ChannelEvent> liveEventChannel = response.body().getChannelEventList();
                Calendar c = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.Z");
                @SuppressLint("SimpleDateFormat") DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                @SuppressLint("SimpleDateFormat") DateFormat df3 = new SimpleDateFormat("HH:mm:ss");
                String todayDate = df2.format(c.getTime());
                long currentTime = c.getTimeInMillis() / 1000;
                for (ChannelEvent channelEvent : liveEventChannel) {
                    if (df2.format(channelEvent.getDisplayDateTime()).contains(todayDate)) {
                        try {
                            long showTime = df.parse(String.valueOf(channelEvent.getDisplayDateTime())).getTime();
                            long displayDuration = df3.parse(channelEvent.getDisplayDuration()).getTime();
                            //long displayHours = displayDuration / 3600;
                            long diff = currentTime - showTime;
                            //long diffHours = diff / (60 * 60) % 24;
                            if (showTime <= currentTime && displayDuration >= diff) {
                            } else {
                                liveEventChannel.remove(channelEvent);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                tvGuideAdapter.addData(liveEventChannel);
                ((MainActivity) mContext).hideProgressDialog();
            }

            @Override
            public void onFailure(Call<EventListResponse> call, Throwable t) {
                ((MainActivity) mContext).showSnackBar(t.toString());
                ((MainActivity) mContext).hideProgressDialog();
            }
        });
    }

    @Override
    public void onResume() {
        ((MainActivity) mContext).setToolbarTitle("TV Guide");
        ((MainActivity) mContext).setHomeAsEnabled(true);
        ((MainActivity) mContext).manageDrawerLayout(true);
        ((MainActivity) mContext).setToolbarColor(getResources().getColor(R.color.colorPrimary));
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }
}
