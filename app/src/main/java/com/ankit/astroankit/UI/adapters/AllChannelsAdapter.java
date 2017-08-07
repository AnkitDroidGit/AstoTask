package com.ankit.astroankit.UI.adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ankit.astroankit.R;
import com.ankit.astroankit.db.DatabaseHelper;
import com.ankit.astroankit.db.DatabaseSingleton;
import com.ankit.astroankit.models.ChannelModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class AllChannelsAdapter extends RecyclerView.Adapter<AllChannelsAdapter.ViewHolder> {
    private List<ChannelModel> channelModelList;
    private AllChannelsAdapter.OnItemClickListener iOnItemClick;
    DatabaseHelper databaseHelper;

    public AllChannelsAdapter(List<ChannelModel> channelModels, OnItemClickListener onItemClickListener) {
        channelModelList = channelModels;
        iOnItemClick = onItemClickListener;
        databaseHelper = DatabaseSingleton.getInstance().getHelper();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_channel_list_item, parent, false);
        return new AllChannelsAdapter.ViewHolder(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ChannelModel channelModel = channelModelList.get(position);
        holder.channelName.setText(channelModel.getTitle());
        holder.channelNumber.setText("CH " + channelModel.getChannelNumber() + "");
        if (databaseHelper.getFavouriteChannelIds().contains(channelModel.getChannelId())) {
            holder.addToFav.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            holder.addToFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        holder.addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!databaseHelper.getFavouriteChannelIds().contains(channelModel.getChannelId())) {
                    holder.addToFav.setImageResource(R.drawable.ic_favorite_white_24dp);
                    databaseHelper.saveFavouriteId(channelModel);
                } else {
                    holder.addToFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    databaseHelper.removeFavouriteId(channelModel.getChannelId());
                }
            }
        });
        holder.bind(channelModel, iOnItemClick);
    }

    @Override
    public int getItemCount() {
        return channelModelList.size();
    }

    public void updateChannels(List<ChannelModel> channelList) {
        channelModelList.addAll(channelList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        channelModelList.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.channel_name)
        TextView channelName;
        @BindView(R.id.channel_number)
        TextView channelNumber;
        @BindView(R.id.add_to_fav)
        ImageButton addToFav;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final ChannelModel chatListModel, final AllChannelsAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(chatListModel);
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(ChannelModel item);

    }

    public void sortByName() {
        Collections.sort(channelModelList, new Comparator<ChannelModel>() {
            @Override
            public int compare(ChannelModel channelModel, ChannelModel t1) {
                return channelModel.getTitle().compareTo(t1.getTitle());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByNumber() {
        Collections.sort(channelModelList, new Comparator<ChannelModel>() {
            @Override
            public int compare(ChannelModel channelModel, ChannelModel t1) {
                return String.valueOf(channelModel.getChannelNumber()).compareTo(String.valueOf(t1.getChannelNumber()));
            }
        });
        notifyDataSetChanged();
    }
}
