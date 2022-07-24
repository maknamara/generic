package br.com.maknamara.generic;

import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.maknamara.component.MobileClassResolver;
import br.com.maknamara.di.DI;
import br.com.maknamara.di.NoMobileClassResolver;
import br.com.maknamara.di.annotation.Inject;

@RunWith(AndroidJUnit4.class)
public class PersonServiceTest {

    @Inject("personService")
    private PersonService personService;

    @BeforeClass
    public static void BeforeClass() {
        DI.setClassResolver(new NoMobileClassResolver());
    }

    @Test
    public void useAppContext() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DI.setClassResolver(new MobileClassResolver(context));
        DI.setBean("context", this);
        DI.inject(this);
        assertNotNull(this.personService);
    }
}
