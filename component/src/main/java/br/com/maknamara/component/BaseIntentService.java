package br.com.maknamara.component;

import android.app.IntentService;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import br.com.maknamara.di.DI;
import br.com.maknamara.di.annotation.Inject;

public abstract class BaseIntentService extends IntentService {

    @Inject
    protected Logger logger;

    public BaseIntentService() {
        super("BIS-" + System.currentTimeMillis());
        DI.inject(this);
    }

    protected boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
