package com.developerx.dunzo.view.store.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.developerx.dunzo.R;
import com.developerx.dunzo.databinding.AdapterStoreItemBinding;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.CatalogItemHolder> {

    private StoreListListener listener;

    public StoreListAdapter(StoreListListener listener) {
        this.listener = listener;
    }

    public interface StoreListListener{
        void onItemClick();
    }

    @NonNull
    @Override
    public CatalogItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterStoreItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_store_item,parent,false);
        return new CatalogItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogItemHolder holder, int position) {
        holder.binding.storeName.setText("Store Name "+ (position+1));
        holder.binding.storeItem.setOnClickListener(v->{
            listener.onItemClick();
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class CatalogItemHolder extends RecyclerView.ViewHolder{
        AdapterStoreItemBinding binding;
        public CatalogItemHolder(@NonNull AdapterStoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
