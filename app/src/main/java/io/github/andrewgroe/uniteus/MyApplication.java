package io.github.andrewgroe.uniteus;

import android.app.Application;
import android.content.Context;

import io.github.andrewgroe.uniteus.di.AppComponent;
import io.github.andrewgroe.uniteus.di.AppModule;
import io.github.andrewgroe.uniteus.di.DaggerAppComponent;
import io.github.andrewgroe.uniteus.di.UtilsModule;

public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

}
