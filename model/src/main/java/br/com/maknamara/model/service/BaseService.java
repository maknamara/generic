package br.com.maknamara.model.service;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface BaseService<T> {
    T findById(Long id) throws Exception;

    List<T> findAll() throws Exception;

    void save(T t) throws Exception;

    void delete(T t) throws Exception;

    void delete(@NonNull Collection<T> list) throws Exception;

    void clearTable() throws SQLException;
}
