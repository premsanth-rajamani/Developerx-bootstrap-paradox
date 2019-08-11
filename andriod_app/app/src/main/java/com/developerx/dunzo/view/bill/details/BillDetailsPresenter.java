package com.developerx.dunzo.view.bill.details;

import com.developerx.base.data.network.BaseApiDisposable;
import com.developerx.base.util.BaseConstants;
import com.developerx.base.view.BasePresenter;
import com.developerx.dunzo.data.Repo;
import com.developerx.dunzo.data.network.response.BillResponse;

import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class BillDetailsPresenter extends BasePresenter implements BillDetailsContract.Presenter {

    private BillDetailsContract.View view;
    private Repo repo;
    private Disposable disposable;

    public BillDetailsPresenter(BillDetailsContract.View view) {
        this.view = view;
        repo = new Repo(view.getContext());
    }

    @Override
    public void dispose() {
        super.dispose();
        if (disposable!=null){
            disposable.dispose();
        }
    }

    @Override
    public void getBill(String fileName) {
        view.showLoader("");
        disposable = repo.getBill(fileName).subscribeWith(new BaseApiDisposable<BillResponse>(view){
            @Override
            public void onNext(Response<BillResponse> response) {
                super.onNext(response);
                view.hideLoader();
                if (response.code()!= BaseConstants.OK){
                    return;
                }
                if (response.body()==null){
                    view.showToast("No Data!");
                    return;
                }
                if (response.body().getData()!=null && response.body().getData().get(0)!=null){
                    return;
                }
                view.showData(response.body().getData().get(0).getData());
            }
        });
    }


}
