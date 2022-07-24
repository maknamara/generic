package br.com.maknamara.generic;

import br.com.maknamara.di.annotation.Inject;
import br.com.maknamara.di.annotation.Injectable;
import br.com.maknamara.model.service.AbstractBaseService;

@Injectable("personService")
public class PersonServiceImpl extends AbstractBaseService<Person, PersonDAO, PersonValidator> implements PersonService {

    public PersonServiceImpl(@Inject PersonDAO dao, @Inject PersonValidator validator) {
        super(dao, validator);
    }
}
