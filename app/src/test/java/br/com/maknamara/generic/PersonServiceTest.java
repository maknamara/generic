package br.com.maknamara.generic;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.maknamara.activity.BaseApplication;
import br.com.maknamara.di.DI;
import br.com.maknamara.di.NoMobileClassResolver;
import br.com.maknamara.di.annotation.Inject;

public class PersonServiceTest {

    @Inject
    private PersonService personService;

    @BeforeClass
    public static void BeforeClass() {
        DI.setClassResolver(new NoMobileClassResolver());
    }

    @Test
    public void injectDependencies() {
        //A classe BaseApplication tem a seguinte linha: DI.setBean("context", this);
        Context context = new BaseApplication();
        DI.inject(this);
        assertNotNull(this.personService);
    }
}
