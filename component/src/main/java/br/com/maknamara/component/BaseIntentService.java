package br.com.maknamara.component;

import android.app.IntentService;

import br.com.maknamara.di.DI;

public abstract class BaseIntentService extends IntentService {

    public BaseIntentService() {
        super("BIS-" + System.currentTimeMillis());
        DI.inject(this);
    }
}
