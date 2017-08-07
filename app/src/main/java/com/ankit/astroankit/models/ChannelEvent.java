package com.ankit.astroankit.models;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/6/17
 **/

public class ChannelEvent {
    @SerializedName("eventID")
    private String eventID;
    @SerializedName("channelId")
    private int channelId;
    @SerializedName("channelStbNumber")
    private String channelStbNumber;
    @SerializedName("channelHD")
    private String channelHD;
    @SerializedName("displayDateTimeUtc")
    private Date displayDateTimeUtc;
    @SerializedName("displayDateTime")
    private Date displayDateTime;
    @SerializedName("displayDuration")
    private String displayDuration;
    @SerializedName("programmeTitle")
    private String programmeTitle;
    @SerializedName("shortSynopsis")
    private String shortSynopsis;
    @SerializedName("genre")
    private String genre;
    @SerializedName("subGenre")
    private String subGenre;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelStbNumber() {
        return channelStbNumber;
    }

    public void setChannelStbNumber(String channelStbNumber) {
        this.channelStbNumber = channelStbNumber;
    }

    public String getChannelHD() {
        return channelHD;
    }

    public void setChannelHD(String channelHD) {
        this.channelHD = channelHD;
    }


    public String getDisplayDuration() {
        return displayDuration;
    }

    public void setDisplayDuration(String displayDuration) {
        this.displayDuration = displayDuration;
    }

    public String getProgrammeTitle() {
        return programmeTitle;
    }

    public void setProgrammeTitle(String programmeTitle) {
        this.programmeTitle = programmeTitle;
    }

    public String getShortSynopsis() {
        return shortSynopsis;
    }

    public void setShortSynopsis(String shortSynopsis) {
        this.shortSynopsis = shortSynopsis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public void setSubGenre(String subGenre) {
        this.subGenre = subGenre;
    }

    public Date getDisplayDateTimeUtc() {
        return displayDateTimeUtc;
    }

    public void setDisplayDateTimeUtc(Date displayDateTimeUtc) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.displayDateTimeUtc = new Date(inputFormat.format(displayDateTimeUtc));

    }

    public Date getDisplayDateTime() {
        return displayDateTime;
    }

    public void setDisplayDateTime(Date displayDateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.displayDateTime = new Date(inputFormat.format(displayDateTime));

    }
}
