package ru.itis.inform.checkers.classcheckers;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class HashExistenceChecker implements ClassChecker {
    public String start(ArrayList<Class> classes) {
        boolean hash = false;
        for (Class curClass : classes) {
            if (curClass.getName().contains("hash") || curClass.getName().contains("Hash")) {
                hash = true;
                break;
            }
            Method methods[] = curClass.getDeclaredMethods();
            for (Method method : methods) {
                Annotation annotations[] = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Bean && method.getReturnType().equals(PasswordEncoder.class)) {
                        hash = true;
                        break;
                    }
                }
            }
        }
        if (hash) {
            return ("Есть хэширование паролей");
        } else {
            return ("Нет хэширования паролей");
        }
    }
}
