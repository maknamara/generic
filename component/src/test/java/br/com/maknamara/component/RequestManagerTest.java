package br.com.maknamara.component;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RequestManagerTest {
    @Test
    public void get() throws Exception {
        RequestManager requestManager = new RequestManager();
        requestManager.get("https://developer.mozilla.org/", response -> {
            System.out.println(response);
            assertFalse(response.toString().isEmpty());
        });
    }
}