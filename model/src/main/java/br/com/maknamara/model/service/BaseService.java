package br.com.maknamara.model.service;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.maknamara.model.BaseEntity;
import br.com.maknamara.model.dao.GenericDAO;
import br.com.maknamara.model.validator.BaseValidator;

public class BaseService<T extends BaseEntity, D extends GenericDAO<T>, V extends BaseValidator<T>> {
    protected D dao;
    protected V validator;

    public BaseService(@NonNull D dao, @NonNull V validator) {
        this.dao = dao;
        this.validator = validator;
    }

    public T findById(Long id) throws Exception {
        return dao.findById(id);
    }

    public List<T> findAll() throws Exception {
        return dao.queryForAll();
    }

    public void save(T t) throws Exception {
        validator.validateRecording(t);
        if (t.getId() == null) {
            if (t.getRegistrationDate() == null) {
                t.setRegistrationDate(new Date());
            }
        }
        dao.save(t);
    }

    public void delete(T t) throws Exception {
        validator.validateDeletion(t);
        dao.delete(t);
    }

    public void delete(@NonNull Collection<T> list) throws Exception {
        for (T t : list) {
            delete(t);
        }
    }

    public void clearTable() throws SQLException {
        dao.clearTable();
    }
}