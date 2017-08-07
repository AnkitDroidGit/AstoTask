package com.ankit.astroankit.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class ExternalReference {
    @SerializedName("system")
    private String system;
    @SerializedName("subSystem")
    private String subSystem;
    @SerializedName("value")
    private String value;
}
