package ru.itis.inform.checkers;

import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class RepositoryChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        boolean crud = false;
        boolean jpa = false;
        for (Class curClass : classes) {
            Class<?>[] superClasses = curClass.getInterfaces();
            for (Class<?> superClass : superClasses) {
                if ("org.springframework.data.jpa.repository.JpaRepository".equals(superClass.getName())) {
                    jpa = true;
                } else {
                    if ("org.springframework.data.repository.CrudRepository".equals(superClass.getName())) {
                        crud = true;
                    }
                }
            }
        }
        if (jpa && crud) {
            return ("В проекте используется JpaRepository и CrudRepository");
        } else {
            if (crud) {
                return ("В проекте используется CrudRepository");
            } else {
                if (jpa) {
                    return ("В проекте используется JpaRepository");
                } else {
                    return ("В проекте не используется ни JpaRepository, ни CrudRepository");
                }
            }
        }
    }
}
