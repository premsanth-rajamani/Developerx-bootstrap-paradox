package com.developerx.dunzo.view.store.list;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.developerx.base.view.BaseActivity;
import com.developerx.dunzo.R;
import com.developerx.dunzo.databinding.ActivityStoreListBinding;
import com.developerx.dunzo.databinding.ToolbarMainBinding;
import com.developerx.dunzo.view.store.details.StoreDetailsActivity;
import com.developerx.dunzo.view.image.preview.ImagePreviewActivity;

import java.io.ByteArrayOutputStream;

public class StoreListActivity extends BaseActivity implements StoreListContract.View, StoreListAdapter.StoreListListener {
    public static final int CAMERA_REQUEST_CODE = 0;
    public static final int GALLERY_REQUEST_CODE = 1;
    public static final int PREVIEW_REQUEST_CODE = 2;
    StoreListPresenter presenter;
    StoreListAdapter adapter;
    ActivityStoreListBinding binding;
    String filter = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_list);
        setToolbar(binding.toolbar,getResources().getString(R.string.app_name));
        presenter = new StoreListPresenter(this);
        setFilterState(filter);
        setListeners();
        setRecyclerView();
    }

    private void setRecyclerView(){
        adapter = new StoreListAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void setListeners(){
        binding.food.setOnClickListener(v->{
            filter = "food";
            setFilterState(filter);
        });
        binding.grocery.setOnClickListener(v->{
            filter = "grocery";
            setFilterState(filter);
        });
        binding.medical.setOnClickListener(v->{
            filter = "medical";
            setFilterState(filter);
        });
        binding.toolbar.search.setOnClickListener(v->{
            binding.toolbar.toolbar.setVisibility(View.GONE);
            binding.searchView.searchLayout.setVisibility(View.VISIBLE);
            binding.category.setVisibility(View.GONE);
        });
        binding.searchView.closeIcon.setOnClickListener(v->{
            binding.searchView.searchLayout.setVisibility(View.GONE);
            binding.toolbar.toolbar.setVisibility(View.VISIBLE);
            binding.category.setVisibility(View.VISIBLE);
        });
        binding.upload.setOnClickListener(v->{
            selectImage();
        });
    }
    private void setFilterState(String category){
        switch (category.toLowerCase()){
            case "food":
                setActive(binding.food,true);
                setActive(binding.grocery,false);
                setActive(binding.medical,false);
                break;
            case "grocery":
                setActive(binding.food,false);
                setActive(binding.grocery,true);
                setActive(binding.medical,false);
                break;
            case "medical":
                setActive(binding.food,false);
                setActive(binding.grocery,false);
                setActive(binding.medical,true);
                break;
            default:
                setActive(binding.food,false);
                setActive(binding.grocery,false);
                setActive(binding.medical,false);
        }
    }

    private void setActive(Button button,boolean isActive){
        button.setActivated(isActive);
        button.setTextColor(isActive?getColor(R.color.colorAccent):getColor(R.color.colorPrimary));
    }
    public void setToolbar(ToolbarMainBinding toolbar, String title){
        setSupportActionBar(toolbar.toolbar);
        toolbar.toolbarTitle.setText(title);
        toolbar.search.setVisibility(View.VISIBLE);
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload catalog");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    requestToTakePic();
                } else if (options[item].equals("Choose from Gallery")) {
                    requestGallery();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void requestToTakePic() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            takePic();
        }else{
            requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }
    }

    private void takePic(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    public void requestGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            picFromGallery();
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_REQUEST_CODE);
        }
    }

    private void picFromGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==CAMERA_REQUEST_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePic();
            }
        }else if (requestCode==GALLERY_REQUEST_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                picFromGallery();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data!=null && data.getExtras()!=null) {
            Intent intent = new Intent(this, ImagePreviewActivity.class);
            intent.putExtra("data", (Bitmap) data.getExtras().get("data"));
            startActivityForResult(intent,PREVIEW_REQUEST_CODE);
        }else if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            Uri selectedImage =  data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Log.d(TAG, "onActivityResult: before if");
            if (selectedImage != null) {
                Log.d(TAG, "onActivityResult: if");
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    Log.d(TAG, "onActivityResult: if if");
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    cursor.close();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Log.d(TAG, "onActivityResult: close");
                    Intent intent = new Intent(this, ImagePreviewActivity.class);
                    intent.putExtra("dataArray", byteArray);
                    startActivityForResult(intent,PREVIEW_REQUEST_CODE);
                    Log.d(TAG, "onActivityResult: called");
                }
            }

        }else if (requestCode==PREVIEW_REQUEST_CODE && resultCode==RESULT_OK){

        }
    }

    @Override
    public void showCatalogs() {

    }

    @Override
    public void onItemClick() {
        Intent intent = new Intent(this, StoreDetailsActivity.class);
        startActivity(intent);
    }
}
