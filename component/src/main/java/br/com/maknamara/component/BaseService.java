package br.com.maknamara.component;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import br.com.maknamara.di.DI;
import br.com.maknamara.di.annotation.Inject;

public abstract class BaseService extends Service {

    @Inject
    protected Logger logger;

    public BaseService() {
        DI.inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
