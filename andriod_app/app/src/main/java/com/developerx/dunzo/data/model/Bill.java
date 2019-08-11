package com.developerx.dunzo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bill {

    @SerializedName("_id")
    @Expose
    private String _id;


    @SerializedName("data")
    @Expose
    private BillData data;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public BillData getData() {
        return data;
    }

    public void setData(BillData data) {
        this.data = data;
    }
}
