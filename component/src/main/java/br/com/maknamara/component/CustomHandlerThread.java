package br.com.maknamara.component;

import android.os.Handler;
import android.os.HandlerThread;

public class CustomHandlerThread extends HandlerThread {
    private Handler handler;

    public CustomHandlerThread(String name) {
        super(name);
        start();
    }

    public boolean addTask(Runnable runnable) {
        initializeIfNeeded();
        return this.handler.post(runnable);
    }

    private void initializeIfNeeded() {
        if (this.handler == null) {
            this.handler = new Handler(getLooper());
        }
    }
}