package com.ankit.astroankit.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class ChannelData {
    @SerializedName("channelId")
    private int channelId;
    @SerializedName("siChannelId")
    private String siChannelId;
    @SerializedName("channelTitle")
    private String channelTitle;
    @SerializedName("channelDescription")
    private String description;
    @SerializedName("channelLanguage")
    private String channelLanguage;
    @SerializedName("channelColor1")
    private String channelColor1;
    @SerializedName("channelColor2")
    private String channelColor2;
    @SerializedName("channelColor3")
    private String channelColor3;
    @SerializedName("channelCategory")
    private String channelCategory;
    @SerializedName("channelStbNumber")
    private String channelStbNumber;
    @SerializedName("channelHD")
    private boolean isHd;
    @SerializedName("hdSimulcastChannel")
    private String hdSimulcastChannel;
    @SerializedName("channelStartDate")
    private Date channelStartDate;
    @SerializedName("channelEndDate")
    private Date channelEndDate;
    @SerializedName("channelExtRef")
    private List<ExternalReference> externalReferences;


}
