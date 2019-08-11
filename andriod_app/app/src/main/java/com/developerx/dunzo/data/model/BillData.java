package com.developerx.dunzo.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillData {

    @SerializedName("productData")
    @Expose
    private List<Product> productData;

    @SerializedName("merchantData")
    @Expose
    private Merchant merchantData;

    public List<Product> getProductData() {
        return productData;
    }

    public void setProductData(List<Product> productData) {
        this.productData = productData;
    }

    public Merchant getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(Merchant merchantData) {
        this.merchantData = merchantData;
    }
}
