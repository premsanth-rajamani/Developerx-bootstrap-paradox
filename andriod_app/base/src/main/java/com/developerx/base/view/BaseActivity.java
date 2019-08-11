package com.developerx.base.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.developerx.base.R;

public class BaseActivity extends AppCompatActivity implements BaseContract.BaseView {

    public final String TAG = this.getClass().getSimpleName();
    private Dialog dialog;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.lottie_loader);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.loader_background));
    }

    @Override
    public void showLoader(String msg) {
        try {
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoader() {
        if (dialog!=null) {
            try {
                dialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

    }

}
