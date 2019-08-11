package com.developerx.base.view.components;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.developerx.base.R;
import com.developerx.base.databinding.LottieLoaderBinding;

public class BaseLoader extends Fragment {

    public LottieLoaderBinding binding;
    public String file;
    @RawRes int res;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.lottie_loader,container,false);
        try {
            binding.lottieLoader.setAnimation(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.lottieLoader.playAnimation();
        binding.lottieLoader.loop(true);
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
