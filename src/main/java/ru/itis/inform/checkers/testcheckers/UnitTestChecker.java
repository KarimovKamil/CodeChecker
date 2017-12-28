package ru.itis.inform.checkers.testcheckers;

import org.junit.Test;
import ru.itis.inform.checkers.Checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class UnitTestChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        int testsCount = 0;
        for (Class curClass : classes) {
            Method methods[] = curClass.getDeclaredMethods();
            for (Method method : methods) {
                Annotation annotations[] = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Test) {
                        testsCount++;
                    }
                }
            }
        }
        return ("Количество Unit-тестов в проекте: " + testsCount);
    }
}
