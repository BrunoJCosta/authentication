package br.com.login;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:public",
        "spring.datasource.username=sa",
        "spring.datasource.password=password"
})
public class FirstTest {

    @Test
    @SuppressWarnings("JUnit5AssertionsConverter")
    void testing() {
        Assert.assertTrue(true);
    }

}
