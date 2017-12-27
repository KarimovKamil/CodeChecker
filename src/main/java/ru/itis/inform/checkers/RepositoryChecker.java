package ru.itis.inform.checkers;

import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class RepositoryChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        int crud = 0;
        int jpa = 0;
        for (int i = 0; i < classes.size(); i++) {
            Class curClass = classes.get(i);
            Class<?>[] superClasses = curClass.getInterfaces();
            for (Class<?> superClass : superClasses) {
                if ("org.springframework.data.jpa.repository.JpaRepository".equals(superClass.getName())) {
                    jpa++;
                } else {
                    if ("org.springframework.data.repository.CrudRepository".equals(superClass.getName())) {
                        crud++;
                    }
                }
            }
        }
        if (jpa != 0 && crud != 0) {
            return ("В проекте используется JpaRepository и CrudRepository");
        } else {
            if (crud != 0) {
                return ("В проекте используется CrudRepository");
            } else {
                if (jpa != 0) {
                    return ("В проекте используется JpaRepository");
                } else {
                    return ("В проекте не используется ни JpaRepository, ни CrudRepository");
                }
            }
        }
    }
}
