package com.developerx.dunzo.view.bill.details;

import com.developerx.base.view.BaseContract;
import com.developerx.dunzo.data.model.BillData;

public interface BillDetailsContract {
    interface View extends BaseContract.BaseView {
        void showData(BillData billData);
    }
    interface Presenter extends BaseContract.BasePresenter {
        void getBill(String fileName);
    }
}
