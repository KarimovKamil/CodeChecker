package ru.itis.inform.checkers;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class ORMChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        int count = 0;
        for (Class curClass : classes) {
            Annotation[] annotations = curClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Entity) {
                    count++;
                }
            }
        }
        return ("Количество ORM-классов в проекте: " + count);
    }
}
