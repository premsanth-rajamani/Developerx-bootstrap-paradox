package com.developerx.base.di.components;

import android.app.Application;
import android.content.Context;

import com.developerx.base.di.ApplicationContext;
import com.developerx.base.di.modules.ApplicationModule;
import com.developerx.base.di.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    void inject(Application application);

}