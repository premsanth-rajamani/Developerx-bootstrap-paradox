package com.developerx.base;

import android.app.Application;

import com.developerx.base.di.components.ApplicationComponent;
import com.developerx.base.di.components.DaggerApplicationComponent;
import com.developerx.base.di.modules.ApplicationModule;

public class CatalogDunzoApplication extends Application {
    private static ApplicationComponent applicationComponent;


    public static ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            new CatalogDunzoApplication();
            applicationComponent = CatalogDunzoApplication.getApplicationComponent();
            return applicationComponent;
        }
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dependencyInjection();
//        BaseSharedPref baseSharedPref = new BaseSharedPref();
//        baseSharedPref.initPreferences(this);

    }

    private void dependencyInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }
}
