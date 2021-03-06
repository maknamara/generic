package br.com.maknamara.component;

import android.content.Context;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import br.com.maknamara.di.NoMobileClassResolver;
import dalvik.system.DexFile;

public class MobileClassResolver extends NoMobileClassResolver {

    private final Context context;

    public MobileClassResolver(Context context) {
        super();
        warning("starting");
        this.context = context;
    }

    @Override
    public Map<String, Class<?>> resolve() {
        try {
            warning("starting");
            Map<String, Class<?>> registered = new HashMap<>();
            DexFile dexFile = new DexFile(context.getPackageCodePath());
            Enumeration<String> enumeration = dexFile.entries();

            while (enumeration.hasMoreElements()) {
                String className = enumeration.nextElement();
                if (className.startsWith("br.com.maknamara") && !className.contains("$")) {
                    registerClassIfAnnotationIsPresent(registered, className);
                }
            }
            return registered;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
