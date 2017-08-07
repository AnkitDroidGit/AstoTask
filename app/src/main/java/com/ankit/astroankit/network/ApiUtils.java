package com.ankit.astroankit.network;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class ApiUtils {
    public static final String BASE_URL = "http://ams-api.astro.com.my";

    public static IAPIService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(IAPIService.class);
    }
}
