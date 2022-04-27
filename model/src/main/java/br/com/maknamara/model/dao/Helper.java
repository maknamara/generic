package br.com.maknamara.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import br.com.maknamara.model.BaseEntity;
import dalvik.system.DexFile;

public class Helper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private List<Class<?>> classes = new ArrayList<>();

    public Helper(Context context) {
        super(context, "database.db", null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            loadClasses();
            for (Class<?> clazz : classes) {
                TableUtils.createTable(connectionSource, clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        try {
            loadClasses();
            for (Class<?> clazz : classes) {
                TableUtils.dropTable(connectionSource, clazz, true);
            }
            onCreate(database, connectionSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            loadClasses();
            for (Class<?> clazz : classes) {
                TableUtils.dropTable(connectionSource, clazz, true);
            }
            onCreate(database, connectionSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadClasses() throws Exception {
        String packageName = BaseEntity.class.getPackage().getName();
        if (classes.isEmpty()) {
            String packageCodePath = context.getPackageCodePath();
            Enumeration<String> enumeration = new DexFile(packageCodePath).entries();
            while (enumeration.hasMoreElements()) {
                String className = enumeration.nextElement();
                if (className.contains(packageName)) {
                    Class<?> clazz = Class.forName(className);
                    if (!BaseEntity.class.equals(clazz) && BaseEntity.class.isAssignableFrom(clazz)) {
                        classes.add(clazz);
                    }
                }
            }
        }
    }
}