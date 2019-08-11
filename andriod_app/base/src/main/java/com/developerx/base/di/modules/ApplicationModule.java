package com.developerx.base.di.modules;

import android.content.Context;

import com.developerx.base.di.ApplicationContext;
import com.developerx.base.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context getContext(){
        return context;
    }
}
