package com.ankit.astroankit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class ChannelResponse {
    @SerializedName("responseCode")
    private String responseCode;
    @SerializedName("responseMessage")
    private String responseMessage;
    @SerializedName("channel")
    private List<ChannelData> channelDataList;
}
