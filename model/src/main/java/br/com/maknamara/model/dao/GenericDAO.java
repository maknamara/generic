package br.com.maknamara.model.dao;

import androidx.annotation.NonNull;

import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

import br.com.maknamara.model.BaseEntity;

public class GenericDAO<T extends BaseEntity> extends BaseDaoImpl<T, Long> {

    public GenericDAO(@NonNull Helper helper, Class<T> clazz) throws SQLException {
        super(clazz);
        setConnectionSource(helper.getConnectionSource());
        initialize();
    }
}