package br.com.login;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class FirstTest {

    @Test
    @SuppressWarnings("JUnit5AssertionsConverter")
    void testing() {
        Assert.assertTrue(true);
    }

}
