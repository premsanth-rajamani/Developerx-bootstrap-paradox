package com.developerx.dunzo.view.store.details;

import com.developerx.base.view.BaseContract;

public interface StoreDetailsContract {
    interface View extends BaseContract.BaseView{
        void showCatalogs();
    }
    interface Presenter extends BaseContract.BasePresenter{
        void getCatalogs(String storeId);
    }
}
