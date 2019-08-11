package com.developerx.dunzo.view.store.details;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.developerx.base.view.BaseActivity;
import com.developerx.dunzo.R;
import com.developerx.dunzo.databinding.ActivityStoreDetailsBinding;
import com.developerx.dunzo.databinding.ToolbarMainBinding;

public class StoreDetailsActivity extends BaseActivity implements StoreDetailsContract.View {

    StoreDetailsPresenter presenter;
    StoreDetailsAdapter adapter;
    ActivityStoreDetailsBinding binding;
    private String storeId;
    private String storeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_details);
        presenter = new StoreDetailsPresenter(this);
        setRecyclerView();
        if (getIntent()!=null && getIntent().getExtras()!=null){
            storeId = getIntent().getExtras().getString("storeId","");
            storeName = getIntent().getExtras().getString("storeName","");
            setToolbar(binding.toolbar, storeName);
            presenter.getCatalogs(storeId);
        }else {
            setToolbar(binding.toolbar, "Details");
        }
    }


    private void setRecyclerView(){
        adapter = new StoreDetailsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setToolbar(ToolbarMainBinding toolbar, String title){
        setSupportActionBar(toolbar.toolbar);
        toolbar.toolbarTitle.setText(title);
        toolbar.search.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    public void showCatalogs() {

    }
}
