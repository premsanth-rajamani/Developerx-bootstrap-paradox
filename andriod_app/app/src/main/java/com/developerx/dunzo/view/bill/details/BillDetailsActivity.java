package com.developerx.dunzo.view.bill.details;

import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.developerx.base.view.BaseActivity;
import com.developerx.dunzo.R;
import com.developerx.dunzo.data.model.BillData;
import com.developerx.dunzo.data.model.Merchant;
import com.developerx.dunzo.data.model.Product;
import com.developerx.dunzo.databinding.ActivityBillDetailsBinding;
import com.developerx.dunzo.databinding.ViewBillMenuDetailsBinding;

import java.util.List;

public class BillDetailsActivity extends BaseActivity implements BillDetailsContract.View {

    ActivityBillDetailsBinding binding;
    BillDetailsPresenter presenter;
    String fileName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_details);
        presenter = new BillDetailsPresenter(this);
        if (getIntent()!=null && getIntent().getExtras()!=null){
            fileName = getIntent().getExtras().getString("fileName");
            presenter.getBill(fileName);
        }
    }


    private void showMerchant(Merchant merchant){
        binding.storeName.setText(merchant!=null && merchant.getName()!=null ? merchant.getName():"NA");
        binding.storeAddress.setText(merchant!=null && merchant.getAddress()!=null ? merchant.getAddress():"NA");
        binding.tin.setText(merchant!=null && merchant.getTinNo()!=null ? merchant.getTinNo():"NA");
    }


    private void showProducts(List<Product> products){
        if (products==null){
            binding.table.setVisibility(View.GONE);
            return;
        }
        for (Product product : products){
            binding.table.addView(addRow(product));
        }
    }

    private View addRow(Product product){
        ViewBillMenuDetailsBinding billMenuDetailsBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.view_bill_menu_details,null,false);
        billMenuDetailsBinding.menuName.setText(product.getName()!=null?product.getName():"");
        billMenuDetailsBinding.menuRate.setText(product.getPrice()!=null?product.getPrice():"");
        billMenuDetailsBinding.menuQty.setText(product.getQuantity()!=null?product.getQuantity():"");
        billMenuDetailsBinding.menuTotal.setText(product.getTotal()!=null?product.getTotal():"");
        return billMenuDetailsBinding.getRoot();
    }

    @Override
    public void showData(BillData billData) {
        if (billData!=null) {
            showMerchant(billData.getMerchantData());
            showProducts(billData.getProductData());
        }else{
            showToast("No Data!");
        }

    }
}
