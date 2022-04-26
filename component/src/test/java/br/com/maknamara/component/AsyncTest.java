package br.com.maknamara.component;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

class AsyncTest {
    @Test
    public void asyncTest() {
        Async<String, Long> async = new Async<String, Long>(params -> {
            List<Long> values = new ArrayList<>();
            for (String param : params) {
                values.add(Long.parseLong(param));
            }
            return values;
        }, result -> {
            for (Long value : result) {
                System.out.print(value);
            }
        }, "0", "1", "2");


        async.start();
    }
}