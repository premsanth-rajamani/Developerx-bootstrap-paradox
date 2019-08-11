package com.developerx.dunzo.view.store.details;

import com.developerx.base.view.BasePresenter;

public class StoreDetailsPresenter extends BasePresenter implements StoreDetailsContract.Presenter {


    private StoreDetailsContract.View view;

    public StoreDetailsPresenter(StoreDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void getCatalogs(String storeId) {

    }
}
