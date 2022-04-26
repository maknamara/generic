package br.com.maknamara.model.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import br.com.maknamara.model.BaseEntity;

public class GenericDAO<T extends BaseEntity> extends BaseDaoImpl<T,Long> {

    public GenericDAO(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        initialize();
    }
}