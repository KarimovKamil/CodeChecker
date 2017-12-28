package ru.itis.inform.checkers.classcheckers;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class MappingChecker implements ClassChecker {
    public String start(ArrayList<Class> classes) {
        Set<String> mappings = new TreeSet<>();

        String classMapping, methodMapping;
        for (Class aClass : classes) {
            classMapping = getClassMapping(aClass);

            for (Method method : aClass.getDeclaredMethods()) {
                methodMapping = getMethodMapping(method);
                if (methodMapping != null) {
                    methodMapping = classMapping + methodMapping;
                    mappings.add(methodMapping);
                }
            }
        }

        StringBuilder builder = new StringBuilder("Different mappings: ")
                .append(mappings.size())
                .append("\n");

        for (String mapping : mappings) {
            builder.append(mapping).append(",\n");
        }

        return builder.toString();
    }

    private String getMethodMapping(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof RequestMapping) {
                RequestMapping requestMapping = (RequestMapping) annotation;
                return requestMapping.value()[0];
            }
        }
        return null;
    }

    private String getClassMapping(Class aClass) {
        for (Annotation annotation : aClass.getAnnotations()) {
            if (annotation instanceof RequestMapping) {
                RequestMapping requestMapping = (RequestMapping) annotation;
                return requestMapping.value()[0];
            }
        }
        return "";
    }
}
