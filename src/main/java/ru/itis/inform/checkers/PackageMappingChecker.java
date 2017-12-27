package ru.itis.inform.checkers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class PackageMappingChecker implements Checker {

    private static final String POS_RESULT = "В проекте все классы лежат в своих пакетах " +
            "(Services, Controllers, DAO/Repository)";

    private static final String NEG_RESULT = "В проекте не все классы лежат в своих пакетах " +
            "(Services, Controllers, DAO/Repository)";

    public String start(ArrayList<Class> classes) {
        Pattern servicePattern = Pattern.compile(".*(S|s)ervice.*");
        Pattern controllerPattern = Pattern.compile(".*(C|c)ontroller.*");
        Pattern daoPattern = Pattern.compile(".*(D|d)ao.*");
        Pattern repositoryPattern = Pattern.compile(".*(R|r)epository.*");
        Matcher pkgMatcher;
        Matcher classMatcher;
        for (Class aClass : classes) {
            int count = 0;
            String classCanonicalName = aClass.getCanonicalName();
            String className = aClass.getSimpleName();
            int index = classCanonicalName.lastIndexOf('.');
            String pkg = classCanonicalName.substring(0, index);

            pkgMatcher = servicePattern.matcher(pkg);
            classMatcher = servicePattern.matcher(className);
            if (classMatcher.matches()) {
                if (!pkgMatcher.matches()) {
                    return NEG_RESULT;
                }
            } else {
                count++;
            }

            pkgMatcher = controllerPattern.matcher(pkg);
            classMatcher = controllerPattern.matcher(className);
            if (classMatcher.matches()) {
                if (!pkgMatcher.matches()) {
                    return NEG_RESULT;
                }
            } else {
                count++;
            }

            pkgMatcher = daoPattern.matcher(pkg);
            classMatcher = daoPattern.matcher(className);
            if (classMatcher.matches()) {
                if (!pkgMatcher.matches()) {
                    return NEG_RESULT;
                }
            } else {
                count++;
            }

            pkgMatcher = repositoryPattern.matcher(pkg);
            classMatcher = repositoryPattern.matcher(className);
            if (classMatcher.matches()) {
                if (!pkgMatcher.matches()) {
                    return NEG_RESULT;
                }
            } else {
                count++;
            }

            if (count == 4) {
                return NEG_RESULT;
            }
        }
        return POS_RESULT;
    }
}
