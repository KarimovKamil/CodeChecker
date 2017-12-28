package ru.itis.inform.checkers.testcheckers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.mockito.Mock;
import ru.itis.inform.checkers.classcheckers.ClassChecker;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class MockChecker implements ClassChecker {
    public String start(ArrayList<Class> classes) {
        for (Class aClass : classes) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Mock) {
                        return "В проекте используются Mock-объекты библиотеки Mockito";
                    }
                }
            }
        }
        return "В проекте не используются Mock-объекты библиотеки Mockito";
    }
}
