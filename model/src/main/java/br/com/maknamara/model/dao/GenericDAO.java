package br.com.maknamara.model.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.Where;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import br.com.maknamara.model.BaseEntity;
import br.com.maknamara.model.validator.BaseValidator;
import br.com.maknamara.tools.RuleChecker;
import br.com.maknamara.tools.ToolReflection;
import br.com.maknamara.tools.ToolString;

public class GenericDAO<T extends BaseEntity> extends BaseDaoImpl<T, Long> {

    private static Logger logger;

    public GenericDAO(@NonNull Helper helper, Class<T> clazz) throws SQLException {
        super(clazz);
        logger = LoggerFactory.getLogger(getClass());
        setConnectionSource(helper.getConnectionSource());
        initialize();
    }

    protected void showSQL(@NonNull StatementBuilder<T, Long> statementBuilder) throws SQLException {
        StackTraceElement st = Thread.currentThread().getStackTrace()[3];
        String tag = getClass().getName() + "." + st.getMethodName();
        String str = statementBuilder.prepareStatementString();
        Log.d(tag, str);
        logger.debug(tag + ": " + str);
        System.out.println(tag + ": " + str);
    }

    protected void createWhereExampleClause(@NonNull StatementBuilder<T, Long> statementBuilder, @NonNull T t) throws Exception {
        ToolReflection toolReflection = new ToolReflection(new ToolString(), new RuleChecker());
        List<Field> fields = toolReflection.getFields(t.getClass());

        Where<T, Long> where = statementBuilder.where();

        where.raw("1 = 1");

        for (Field field : fields) {
            if (!"id,registrationDate".contains(field.getName()) && !Collection.class.isAssignableFrom(field.getType())) {
                boolean isAccessible = field.isAccessible();

                field.setAccessible(true);
                Object value = field.get(t);
                field.setAccessible(isAccessible);

                if (value != null) {
                    if (BaseEntity.class.isAssignableFrom(field.getType())) {
                        Long idValue = ((BaseEntity) value).getId();
                        if (idValue != null) {
                            where.and();
                            where.like(field.getName() + "_id", "%" + value + "%");
                        }
                    } else if (CharSequence.class.isAssignableFrom(field.getType())) {
                        if (!BaseValidator.getEmptyIfNull(value.toString()).isEmpty()) {
                            where.and();
                            where.like(field.getName(), "%" + value + "%");
                        }
                    } else {
                        where.and();
                        where.eq(field.getName(), value);
                    }
                }
            }
        }
    }

    public void clearTable() throws SQLException {
        DeleteBuilder<T, Long> delete = deleteBuilder();
        delete.where().raw("1 = 1");
        showSQL(delete);
        delete.delete();
    }

    public T findById(Long id) throws SQLException {
        QueryBuilder<T, Long> qb = queryBuilder();
        qb.where().eq("id", id);
        showSQL(qb);
        return qb.queryForFirst();
    }

    public void save(T t) throws SQLException {
        createOrUpdate(t);
    }
}