package ru.itis.inform.checkers.classcheckers;

import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class SpringSecurityExistenceChecker implements ClassChecker {
    public String start(ArrayList<Class> classes) {
        for (Class aClass : classes) {
            if ("org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter"
                    .equals(aClass.getSuperclass().getName())) {
                return "В проекте используется Spring Security";
            }
        }

        return "В проекте не используется Spring Security";
    }
}
