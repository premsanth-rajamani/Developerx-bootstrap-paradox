package com.developerx.dunzo.data;

import android.content.Context;

import com.developerx.base.data.BaseRepo;
import com.developerx.base.data.network.BaseNetworkInterface;
import com.developerx.base.data.network.response.BaseResponse;
import com.developerx.dunzo.data.network.response.BillResponse;
import com.developerx.dunzo.data.network.response.ImageResponse;
import com.developerx.dunzo.data.network.response.StoreListResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Response;


public class Repo extends BaseRepo {

    public Repo(Context context) {
        super(context);
    }

    public Observable<Response<ImageResponse>> uploadImage(MultipartBody.Part image){
        return getNetworkClient().getRetrofit().create(NetworkInterface.class)
                .uploadImage(image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<StoreListResponse>> getStores(){
        return getNetworkClient().getRetrofit().create(NetworkInterface.class)
                .getStores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<BaseResponse>> getCatalogDetails(){
        return getNetworkClient().getRetrofit().create(NetworkInterface.class)
                .getCatalogDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<BillResponse>> getBill(String fileName){
        return getNetworkClient().getRetrofit().create(NetworkInterface.class)
                .getBill(fileName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
