package com.developerx.base.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.developerx.base.data.BaseRepo;
import com.developerx.base.di.ApplicationScope;
import com.developerx.base.di.modules.NetworkModule;
import com.developerx.base.di.modules.SharedPrefModule;

import dagger.Component;
import retrofit2.Retrofit;

@ApplicationScope
@Component(modules = {SharedPrefModule.class, NetworkModule.class})
public interface RepoComponent {

    Retrofit getRetrofit();

    SharedPreferences getSharedPreference();

    Context getContext();

    void inject(BaseRepo baseRepo);
}