package com.developerx.dunzo.view.store.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.developerx.dunzo.R;
import com.developerx.dunzo.databinding.AdapterCatalogItemBinding;

public class StoreDetailsAdapter extends RecyclerView.Adapter<StoreDetailsAdapter.CatalogHolder> {

    @NonNull
    @Override
    public CatalogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterCatalogItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_catalog_item,parent,false);
        return new CatalogHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogHolder holder, int position) {
        holder.binding.menuName.setText("Menu "+(position+1));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class CatalogHolder extends RecyclerView.ViewHolder{

        AdapterCatalogItemBinding binding;
        public CatalogHolder(@NonNull AdapterCatalogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
