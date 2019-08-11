package com.developerx.base.di.modules;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.developerx.base.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefModule {

    private Context context;
    public SharedPrefModule(Context context){
        this.context = context;
    }

    @ApplicationScope
    @Provides
    Context getContext(){
        return context;
    }
    
    @ApplicationScope
    @Provides
    SharedPreferences getSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
