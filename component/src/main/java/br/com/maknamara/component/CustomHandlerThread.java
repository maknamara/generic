package br.com.maknamara.component;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

public class CustomHandlerThread extends HandlerThread {
    //TODO Ler: https://medium.com/@ali.muzaffar/handlerthreads-and-why-you-should-be-using-them-in-your-android-apps-dc8bf1540341
    //https://www.youtube.com/watch?v=n0mkYSjldeA
    private Handler handler;

    public CustomHandlerThread() {
        this("cht_" + System.currentTimeMillis());
    }

    public CustomHandlerThread(String name) {
        super(name, Process.THREAD_PRIORITY_BACKGROUND);
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