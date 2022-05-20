package br.com.maknamara.generic;

import br.com.maknamara.annotation.Inject;
import br.com.maknamara.model.service.BaseService;

public class PersonService extends BaseService<Person, PersonDAO, PersonValidator> {

    public PersonService(@Inject PersonDAO dao, @Inject PersonValidator validator) {
        super(dao, validator);
    }
}
