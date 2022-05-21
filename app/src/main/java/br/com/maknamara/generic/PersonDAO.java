package br.com.maknamara.generic;

import java.sql.SQLException;

import br.com.maknamara.di.annotation.Inject;
import br.com.maknamara.model.dao.GenericDAO;
import br.com.maknamara.model.dao.Helper;

public class PersonDAO extends GenericDAO<Person> {

    public PersonDAO(@Inject Helper helper) throws SQLException {
        super(helper, Person.class);
    }
}
