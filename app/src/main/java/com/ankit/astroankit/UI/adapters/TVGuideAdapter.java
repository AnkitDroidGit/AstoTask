package com.ankit.astroankit.UI.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankit.astroankit.R;
import com.ankit.astroankit.models.ChannelEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/6/17
 **/

public class TVGuideAdapter extends RecyclerView.Adapter<TVGuideAdapter.ViewHolder> {
    private List<ChannelEvent> channelEventList;

    public TVGuideAdapter(List<ChannelEvent> eventList) {
        channelEventList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tv_guide_item, parent, false);
        return new TVGuideAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChannelEvent channelEvent = channelEventList.get(position);
        holder.channelNumber.setText(channelEvent.getChannelStbNumber());
        holder.showName.setText(channelEvent.getProgrammeTitle());
        holder.showTime.setText(channelEvent.getDisplayDuration());
    }

    @Override
    public int getItemCount() {
        return channelEventList == null ? 0 : channelEventList.size();
    }

    public void clearItems() {
        channelEventList.clear();
    }

    public void addData(List<ChannelEvent> eventList) {
        channelEventList.addAll(eventList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.channel_number_tv)
        TextView channelNumber;
        @BindView(R.id.show_name)
        TextView showName;
        @BindView(R.id.show_time)
        TextView showTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
