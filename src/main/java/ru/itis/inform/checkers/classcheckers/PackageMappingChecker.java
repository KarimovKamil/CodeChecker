package ru.itis.inform.checkers.classcheckers;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.itis.inform.checkers.Checker;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class PackageMappingChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        String posResult = "В проекте все классы лежат в своих пакетах " +
                "(Services, Controllers, DAO/Repository)";
        String negResult = "В проекте не все классы лежат в своих пакетах " +
                "(Services, Controllers, DAO/Repository)";

        Pattern servicePattern = Pattern.compile(".*(S|s)ervice.*");
        Pattern controllerPattern = Pattern.compile(".*(C|c)ontroller.*");
        Pattern daoPattern = Pattern.compile(".*(D|d)ao.*");
        Pattern repositoryPattern = Pattern.compile(".*(R|r)epository.*");
        next:
        for (Class currentClass : classes) {
            String className = currentClass.getSimpleName();
            int index = currentClass.getCanonicalName().lastIndexOf('.');
            String pkg = currentClass.getCanonicalName().substring(0, index);
            Annotation[] annotations = currentClass.getAnnotations();

            Matcher servicePkgMatcher = servicePattern.matcher(pkg);
            Matcher serviceClassMatcher = servicePattern.matcher(className);
            Matcher controllerPkgMatcher = controllerPattern.matcher(pkg);
            Matcher controllerClassMatcher = controllerPattern.matcher(className);
            Matcher daoPkgMatcher = daoPattern.matcher(pkg);
            Matcher daoClassMatcher = daoPattern.matcher(className);
            Matcher repositoryPkgMatcher = repositoryPattern.matcher(pkg);
            Matcher repositoryClassMatcher = repositoryPattern.matcher(className);

            if (servicePkgMatcher.matches()) {
                if (serviceClassMatcher.matches()) {
                    continue;
                } else {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Service) {
                            continue next;
                        }
                    }
                }
                return negResult;
            } else if (controllerPkgMatcher.matches()) {
                if (controllerClassMatcher.matches()) {
                    continue;
                } else {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Controller) {
                            continue next;
                        }
                    }
                }
                return negResult;
            } else if (daoPkgMatcher.matches()) {
                if (daoClassMatcher.matches()) {
                    continue;
                } else {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Repository) {
                            continue next;
                        }
                    }
                }
                return negResult;
            } else if (repositoryPkgMatcher.matches()) {
                if (repositoryClassMatcher.matches()) {
                    continue;
                } else {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Repository) {
                            continue next;
                        }
                    }
                }
                return negResult;
            }
        }
        return posResult;
    }
}
