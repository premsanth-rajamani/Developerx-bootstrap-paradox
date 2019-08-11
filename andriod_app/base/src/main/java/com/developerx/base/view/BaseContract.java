package com.developerx.base.view;

import android.content.Context;

public interface BaseContract {
    interface BaseView{
        Context getContext();
        void showLoader(String msg);
        void hideLoader();
        void showToast(String msg);
        void finish();
    }
    interface BasePresenter{
        void dispose();
    }
}
