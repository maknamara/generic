package br.com.maknamara.model.di;

import androidx.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import br.com.maknamara.model.annotation.Inject;

@SuppressWarnings({"unchecked"})
public class DI {
    private static final Map<String, Object> beans = new HashMap<>();

    private DI() {
    }

    public static void inject(@NonNull Object object) {
        try {
            System.out.println(DI.class.getName() + ".inject in - " + object.getClass().getName());
            injectDependenciesByFields(object);
            System.out.println(DI.class.getName() + ".inject in - " + object.getClass().getName() + " - DONE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object newBean(Class<?> clazz) throws Exception {
        System.out.println(DI.class.getName() + ".newBean: " + clazz.getName());

        Object bean = null;

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length == 0) {
                bean = constructor.newInstance();
            } else {
                Annotation[][] allAnnotations = constructor.getParameterAnnotations();
                Object[] args = new Object[types.length];
                for (int index = 0; index < types.length; index++) {
                    Class<?> type = types[index];
                    for (Annotation annotation : allAnnotations[index]) {
                        if (annotation instanceof Inject) {
                            String name = ((Inject) annotation).value();
                            if (name.isEmpty()) {
                                name = type.getName();
                            }
                            Object arg = beans.get(name);
                            if (arg == null) {
                                arg = newBean(type);
                                beans.put(name, arg);
                            }
                            args[index] = arg;
                        }
                    }
                }
                bean = constructor.newInstance(args);
            }
            break;
        }
        System.out.println(DI.class.getName() + ".newBean: " + clazz.getName() + " - DONE");
        return bean;
    }

    private static void injectDependenciesByFields(Object object) throws Exception {
        System.out.println(DI.class.getName() + ".injectDependenciesByFields");
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
                        bean = newBean(field.getType());
                        beans.put(name, bean);
                    }

                    field.set(object, bean);
                    field.setAccessible(isAccessible);

                    inject(bean);
                }
            }
        }
        System.out.println(DI.class.getName() + ".injectDependenciesByFields - DONE");
    }

    public static <T> T getBean(String key) {
        return (T) beans.get(key);
    }

    public static void setBean(String key, Object object) {
        DI.beans.put(key, object);
    }
}
