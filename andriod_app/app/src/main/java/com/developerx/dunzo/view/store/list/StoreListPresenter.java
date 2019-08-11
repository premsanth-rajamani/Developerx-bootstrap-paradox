package com.developerx.dunzo.view.store.list;

import com.developerx.base.data.network.BaseApiDisposable;
import com.developerx.base.util.BaseConstants;
import com.developerx.base.view.BasePresenter;
import com.developerx.dunzo.data.Repo;
import com.developerx.dunzo.data.network.response.StoreListResponse;

import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class StoreListPresenter extends BasePresenter implements StoreListContract.Presenter {

    private final StoreListContract.View view;
    private Repo repo;
    private Disposable disposable;
    public StoreListPresenter(StoreListContract.View view) {
        this.view = view;
        repo= new Repo(view.getContext());
    }

    @Override
    public void getStores(String filter) {
        view.showLoader("");
        disposable = repo.getStores().subscribeWith(new BaseApiDisposable<StoreListResponse>(view){
            @Override
            public void onNext(Response<StoreListResponse> response) {
                super.onNext(response);
                view.hideLoader();
                if (response.code()!= BaseConstants.OK){
                    return;
                }

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
