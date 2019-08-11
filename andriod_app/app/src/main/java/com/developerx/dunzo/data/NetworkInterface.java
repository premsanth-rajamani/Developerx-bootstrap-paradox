package com.developerx.dunzo.data;

import com.developerx.base.data.network.BaseNetworkInterface;
import com.developerx.base.data.network.response.BaseResponse;
import com.developerx.dunzo.data.network.response.BillResponse;
import com.developerx.dunzo.data.network.response.ImageResponse;
import com.developerx.dunzo.data.network.response.StoreListResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface NetworkInterface{

    @Multipart
    @POST("product/img-to-text")
    Observable<Response<ImageResponse>> uploadImage(
            @Part MultipartBody.Part image);

    @GET("product/img-to")
    Observable<Response<StoreListResponse>> getStores();

    @GET("product/details")
    Observable<Response<BaseResponse>> getCatalogDetails();

    @GET("image-parse")
    Observable<Response<BillResponse>> getBill(
            @Query("image") String fileName
    );


}
