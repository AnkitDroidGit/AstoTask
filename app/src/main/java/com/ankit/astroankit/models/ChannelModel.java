package com.ankit.astroankit.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class ChannelModel {
    @SerializedName("channelId")
    private int channelId;
    @SerializedName("channelTitle")
    private String title;
    @SerializedName("channelStbNumber")
    private int channelNumber;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }
}
