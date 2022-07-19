package br.com.maknamara.component;

import android.util.Log;

public class Logger {
    public void log(Class<?> clazz, String message) {
        log(clazz, message, null);
    }

    public void log(Class<?> clazz, String message, Throwable t) {
        if (t == null) {
            Log.d(clazz.getName(), message);
        } else {
            Log.d(clazz.getName(), message, t);
        }
    }
}
