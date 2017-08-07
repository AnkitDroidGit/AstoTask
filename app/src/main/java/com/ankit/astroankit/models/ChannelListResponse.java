package com.ankit.astroankit.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class ChannelListResponse {
    @SerializedName("responseMessage")
    private String message;
    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("channels")
    private List<ChannelModel> channelList = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<ChannelModel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<ChannelModel> channelList) {
        this.channelList = channelList;
    }
}
