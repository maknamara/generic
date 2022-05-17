package br.com.maknamara.activity;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import br.com.maknamara.component.Inject;

@SuppressWarnings({"unchecked"})
public class BaseApplication extends Application {
    private static Context context;
    private Map<String, Object> beans = new HashMap<>();

    public BaseApplication() {
        super();
        BaseApplication.context = this;
    }

    public void inject(@NonNull Object object) {
        try {
            Field[][] allFields = {object.getClass().getFields(), object.getClass().getDeclaredFields()};

            for (Field[] fields : allFields) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        String name = field.getAnnotation(Inject.class).value();

                        if (name.isEmpty()) {
                            name = field.getType().getName();
                        }

                        boolean isAccessible = field.isAccessible();
                        field.setAccessible(true);

                        Object bean = beans.get(name);
                        if (bean == null) {
                            bean = field.getType().newInstance();
                            beans.put(name, bean);
                        }

                        field.set(object, bean);
                        field.setAccessible(isAccessible);

                        inject(bean);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return BaseApplication.context;
    }

    public final <T> T get(@NonNull String name) {
        return (T) beans.get(name);
    }
}
