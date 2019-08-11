package com.developerx.dunzo.view.image.preview;

import com.developerx.base.view.BaseContract;

import okhttp3.MultipartBody;

public interface ImagePreviewContract {
    interface View extends BaseContract.BaseView {
        void uploadSuccessFull(String fileName);
    }
    interface Presenter extends BaseContract.BasePresenter {
        void uploadImage(MultipartBody.Part image);
    }
}
