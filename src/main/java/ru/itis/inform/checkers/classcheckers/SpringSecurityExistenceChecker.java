package ru.itis.inform.checkers.classcheckers;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class SpringSecurityExistenceChecker implements ClassChecker {
    public String start(ArrayList<Class> classes) {
        for (Class aClass : classes) {
            Annotation[] annotations = aClass.getAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof EnableWebSecurity) {
                    return "В проекте используется Spring Security";
                }
            }
        }

        return "В проекте не используется Spring Security";
    }
}
