package com.developerx.dunzo.data.network.response;

import com.developerx.base.data.network.response.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreListResponse extends BaseResponse {

    @SerializedName("")
    @Expose
    private String data;
}
