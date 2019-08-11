package com.developerx.base.data;

import android.content.Context;

import com.developerx.base.data.local.BaseSharedPref;
import com.developerx.base.data.network.BaseNetworkClient;
import com.developerx.base.di.components.DaggerRepoComponent;
import com.developerx.base.di.components.RepoComponent;
import com.developerx.base.di.modules.NetworkModule;
import com.developerx.base.di.modules.SharedPrefModule;
import com.developerx.base.util.BaseConstants;
import com.developerx.base.util.PreferenceConstant;

import javax.inject.Inject;


public class BaseRepo {

    @Inject
    BaseNetworkClient baseNetworkClient;

    @Inject
    BaseSharedPref baseSharedPref;

    Context context;

    private RepoComponent repoComponent;

    public BaseRepo(Context context){
        this.context = context;
        inject(BaseConstants.BASE_URL);
    }

    public BaseRepo(Context context,String baseUrl){
        this.context = context;
        inject(baseUrl);
    }

    public void inject(String baseUrl){
//        context = ChurchTalkApplication.getApplicationComponent().getContext();
        repoComponent = DaggerRepoComponent.builder()
                .networkModule(new NetworkModule(baseUrl,context))
                .sharedPrefModule(new SharedPrefModule(context))
                .build();
        repoComponent.inject(this);
    }
    public String getAuthToken(){
        return getBaseSharedPref().getPreference(PreferenceConstant.AUTH_TOKEN);
    }

    public BaseNetworkClient getBaseNetworkClient() {
        return baseNetworkClient;
    }

    public BaseSharedPref getBaseSharedPref() {
        return baseSharedPref;
    }
    public BaseNetworkClient getNetworkClient() {
        return baseNetworkClient;
    }

}
