package br.com.login.configuration;

import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@ActiveProfiles("test")
public @interface ApplicationSpringTest {

}

