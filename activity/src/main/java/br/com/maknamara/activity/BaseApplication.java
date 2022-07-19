package br.com.maknamara.activity;

import android.app.Application;

import br.com.maknamara.component.MobileClassResolver;
import br.com.maknamara.di.DI;

public class BaseApplication extends Application {

    public BaseApplication() {
        super();
        DI.setClassResolver(new MobileClassResolver(this));
        DI.setBean("context", this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
