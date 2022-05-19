package br.com.maknamara.generic;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import org.junit.Test;

import br.com.maknamara.activity.BaseApplication;
import br.com.maknamara.model.annotation.Inject;
import br.com.maknamara.model.di.DI;

public class PersonServiceTest {

    @Inject
    private PersonService personService;

    @Test
    public void injectDependencies() {
        Context appContext = new BaseApplication();
        DI.setBean("context", appContext);
        DI.inject(this);
        assertNotNull(this.personService);
    }
}
