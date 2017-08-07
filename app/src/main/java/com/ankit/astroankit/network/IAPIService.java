package com.ankit.astroankit.network;

import com.ankit.astroankit.models.ChannelListResponse;
import com.ankit.astroankit.models.ChannelResponse;
import com.ankit.astroankit.models.EventListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public interface IAPIService {
    @GET("/ams/v3/getChannelList")
    Call<ChannelListResponse> getChannelList();

    @GET("/ams/v3/getChannels")
    Call<ChannelResponse> getChannels(@Query("channelId") int tags);

    @GET("/ams/v3/getEvents")
    Call<EventListResponse> getEvents(@Query("channelId") String tags,
                                      @Query("periodStart") String periodStart,
                                      @Query("periodEnd") String periodEnd);
}
