package com.developerx.dunzo.view.image.preview;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.developerx.base.view.BaseActivity;
import com.developerx.dunzo.R;
import com.developerx.dunzo.databinding.ActivityMainBinding;
import com.developerx.dunzo.databinding.ToolbarMainBinding;
import com.developerx.dunzo.view.bill.details.BillDetailsActivity;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImagePreviewActivity extends BaseActivity implements ImagePreviewContract.View {


    ActivityMainBinding binding;
    TextRecognizer textRecognizer;
    ImagePreviewPresenter presenter;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setToolbar(binding.toolbar,"Preview Catalog");
        if (getIntent()!=null && getIntent().getExtras()!=null){
            bitmap = (Bitmap) getIntent().getExtras().get("data");
            byte[] byteArray = getIntent().getByteArrayExtra("dataArray");
            if (byteArray!=null){
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
            if (bitmap!=null){
                binding.imageView.setImageBitmap(bitmap);
            }
        }
        binding.cam.setOnClickListener(v->{
            if (bitmap!=null){
                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), baos.toByteArray());
                MultipartBody.Part image = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
                presenter.uploadImage(image);
            }
        });
        presenter = new ImagePreviewPresenter(this);
        textRecognizer = new TextRecognizer.Builder(this).build();
    }

    public void setToolbar(ToolbarMainBinding toolbar, String title){
        setSupportActionBar(toolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.search.setVisibility(View.GONE);
        toolbar.toolbarTitle.setText(title);
    }

    @Override
    public void uploadSuccessFull(String fileName) {
        Intent intent = new Intent(this, BillDetailsActivity.class);
        intent.putExtra("fileName",fileName);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
