package com.developerx.dunzo.view.store.list;

import com.developerx.base.view.BaseContract;

public interface StoreListContract {
    interface View extends BaseContract.BaseView {
        void showCatalogs();
    }
    interface Presenter extends BaseContract.BasePresenter {
        void getStores(String filter);
    }
}
