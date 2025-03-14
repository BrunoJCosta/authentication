package br.com.login.configuration.actuator;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Endpoint(id = "log")
@Component
public class Controller {

    @ReadOperation
    public Map<Object, Object> logger() {
        String pasta = "br.com.login.controller";
        Reflections r = new Reflections(
                pasta,
                new SubTypesScanner(false),
                ClasspathHelper.forClassLoader()
        );
        Set<Class<?>> classes = r.getSubTypesOf(Object.class);


        Map<String, List<String>> collect = classes.stream().collect(Collectors.toMap(Class::getSimpleName,
                c -> Arrays.stream(c.getMethods())
                        .filter(Controller::possuiAnnotationGetPostDeletePatch)
                        .map(Method::getName).toList()));

        return classes.stream().collect(Collectors.toMap(Class::getSimpleName,
                c -> collect.get(c.getSimpleName())));
    }

    private static boolean possuiAnnotationGetPostDeletePatch(Method method) {
        return method.isAnnotationPresent(PostMapping.class) ||
             method.isAnnotationPresent(GetMapping.class) ||
             method.isAnnotationPresent(DeleteMapping.class) ||
             method.isAnnotationPresent(PatchMapping.class);

    }
}
