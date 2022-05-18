package br.com.maknamara.model.service;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

import br.com.maknamara.model.BaseEntity;
import br.com.maknamara.model.dao.GenericDAO;
import br.com.maknamara.model.validator.BaseValidator;

public class BaseService<T extends BaseEntity, D extends GenericDAO<T>, V extends BaseValidator<T>> {
    protected D dao;
    protected V validador;

    public BaseService(@NonNull D dao, @NonNull V validador) {
        this.dao = dao;
        this.validador = validador;
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