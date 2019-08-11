package com.developerx.dunzo.data.network.response;

import com.developerx.base.data.network.response.BaseResponse;
import com.developerx.dunzo.data.model.Bill;
import com.developerx.dunzo.data.model.BillData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<Bill> data;

    public List<Bill> getData() {
        return data;
    }

    public void setData(List<Bill> data) {
        this.data = data;
    }
}
