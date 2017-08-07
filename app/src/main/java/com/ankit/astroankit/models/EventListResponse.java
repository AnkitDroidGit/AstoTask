package com.ankit.astroankit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/6/17
 **/

public class EventListResponse {
    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("getevent")
    private List<ChannelEvent> channelEventList;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<ChannelEvent> getChannelEventList() {
        return channelEventList;
    }

    public void setChannelEventList(List<ChannelEvent> channelEventList) {
        this.channelEventList = channelEventList;
    }
}
