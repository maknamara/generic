package br.com.maknamara.generic;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import org.junit.Test;

import br.com.maknamara.DI;
import br.com.maknamara.activity.BaseApplication;
import br.com.maknamara.annotation.Inject;

public class PersonServiceTest {

    @Inject
    private PersonService personService;

    @Test
    public void injectDependencies() {
        //A classe BaseApplication tem a seguinte linha: DI.setBean("context", this);
        Context context = new BaseApplication();
        DI.inject(this);
        assertNotNull(this.personService);
    }
}
