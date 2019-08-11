package com.developerx.dunzo.view.image.preview;

import com.developerx.base.data.network.BaseApiDisposable;
import com.developerx.base.data.network.response.BaseResponse;
import com.developerx.base.view.BasePresenter;
import com.developerx.dunzo.data.Repo;
import com.developerx.dunzo.data.network.response.ImageResponse;

import io.reactivex.disposables.Disposable;
import okhttp3.MultipartBody;
import retrofit2.Response;

public class ImagePreviewPresenter extends BasePresenter implements ImagePreviewContract.Presenter {

    private Repo repo;
    ImagePreviewContract.View view;
    Disposable disposable;

    public ImagePreviewPresenter(ImagePreviewContract.View view) {
        this.view = view;
        repo = new Repo(view.getContext());
    }

    @Override
    public void uploadImage(MultipartBody.Part image) {
        view.showLoader("");
        disposable = repo.uploadImage(image).subscribeWith(new BaseApiDisposable<ImageResponse>(view){
            @Override
            public void onNext(Response<ImageResponse> response) {
                super.onNext(response);
                view.hideLoader();
                if (response.code()!=200){
                    return;
                }
                view.showToast(response.body().getMessage());
                view.uploadSuccessFull(response.body().getData());
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        if (disposable!=null){
            disposable.dispose();
        }
    }
}
