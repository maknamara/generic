package br.com.maknamara.generic;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import br.com.maknamara.model.DI;
import br.com.maknamara.model.annotation.Inject;
@Ignore
public class PersonServiceTest {

    @Inject
    private PersonService personService;

    //public void c(@Inject String a, String b, @Inject String c) {}

    @Test
    public void x() {
/*
        Method[] methods = PersonServiceTest.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().contains("c")) {
                method.getParameterAnnotations();
                method.getParameterTypes();
            }
        }
*/
        DI.setBean("context", new Object());
        DI.inject(this);
        assertNotNull(this.personService);
    }
}
