package com.developerx.base.data.network;


import android.util.Log;

import com.developerx.base.data.network.response.BaseResponse;
import com.developerx.base.util.BaseConstants;
import com.developerx.base.view.BaseContract;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public class BaseApiDisposable<T> extends DisposableObserver<Response<T>> {


    public final static String TAG = BaseApiDisposable.class.getSimpleName();
    private BaseContract.BaseView view;

    public BaseApiDisposable(BaseContract.BaseView view) {
        this.view = view;
    }

    @Override
    public void onNext(Response<T> response) {
        Response<BaseResponse> baseResponse = (Response<BaseResponse>) response;
        switch (response.code()) {
            case BaseConstants.OK:
//                view.showToast(baseResponse.body().getMessage());
                break;

            case BaseConstants.INTERNAL_SERVER_ERROR:
                if (baseResponse.body()!=null && baseResponse.body().getError()!=null && baseResponse.body().getError().getMessage()!=null){
                    view.showToast(baseResponse.body().getError().getMessage());
                }else if(baseResponse.body()!=null && baseResponse.body().getMessage()!=null){
                    view.showToast(baseResponse.body().getMessage());
                }else{
                    view.showToast("Something went wrong!");
                }
                break;

            case BaseConstants.RESOURCE_NOT_FOUND:
                if (baseResponse.body() !=null && baseResponse.body().getMessage()!=null) {
                    view.showToast(baseResponse.body().getMessage());
                }else{
                    view.showToast(baseResponse.message());
                }
                break;

            case BaseConstants.ToKEN_EXPIRED:
                break;

            case BaseConstants.REFRESH_TOKEN_EXPIRED:
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        view.hideLoader();
        view.showToast(e.getLocalizedMessage());
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        view.hideLoader();
    }
}