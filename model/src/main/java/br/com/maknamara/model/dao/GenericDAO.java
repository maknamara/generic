package br.com.maknamara.model.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.StatementBuilder;

import java.sql.SQLException;

import br.com.maknamara.model.BaseEntity;

public class GenericDAO<T extends BaseEntity> extends BaseDaoImpl<T, Long> {

    private static Logger logger;

    public GenericDAO(@NonNull Helper helper, Class<T> clazz) throws SQLException {
        super(clazz);
        logger = LoggerFactory.getLogger(getClass());
        setConnectionSource(helper.getConnectionSource());
        initialize();
    }

    public void clearTable() throws SQLException {
        DeleteBuilder<T, Long> delete = deleteBuilder();
        delete.where().raw("1 = 1");
        showSQL(delete);
        delete.delete();
    }

    public void showSQL(@NonNull StatementBuilder<T, Long> statementBuilder) throws SQLException {
        StackTraceElement st = Thread.currentThread().getStackTrace()[3];
        String tag = getClass().getName() + "." + st.getMethodName();
        String str = statementBuilder.prepareStatementString();
        logger.debug(tag + str);
        Log.d(tag, str);
        System.out.println(tag + str);
    }
}