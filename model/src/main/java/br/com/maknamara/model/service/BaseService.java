package br.com.maknamara.model.service;

import android.content.Context;

import com.j256.ormlite.support.ConnectionSource;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import br.com.maknamara.model.BaseEntity;
import br.com.maknamara.model.dao.GenericDAO;
import br.com.maknamara.model.dao.Helper;
import br.com.maknamara.model.validator.BaseValidator;

public class BaseService<T extends BaseEntity, D extends GenericDAO<T>, V extends BaseValidator<T>> {
    protected D dao;
    protected V validador;
    private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<D> daoClass = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    private Class<V> validatorClass = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];

    public BaseService(Context context) throws Exception {
        Helper helper = new Helper(context);
        dao = daoClass.getDeclaredConstructor(ConnectionSource.class).newInstance(helper.getConnectionSource());
        validador = validatorClass.getDeclaredConstructor().newInstance();
    }

    public List<T> findAll() throws Exception {
        return dao.queryForAll();
    }

    public void save(T t) throws Exception {
        validador.validateRecording(t);
        if (t.getId() == null) {
            t.setRegistrationDate(new Date());
        }
        dao.createOrUpdate(t);
    }

    public void delete(T t) throws Exception {
        validador.validateDeletion(t);
        dao.delete(t);
    }

    public void delete(List<T> list) throws Exception {
        for (T t : list) {
            delete(t);
        }
    }
}