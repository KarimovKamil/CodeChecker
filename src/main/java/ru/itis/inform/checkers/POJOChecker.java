package ru.itis.inform.checkers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class POJOChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        Pattern pattern = Pattern.compile("^(get|set).*");
        Matcher matcher;
        ArrayList<Class> pojos = new ArrayList<>();
        next:
        for (Class aClass : classes) {
            if (aClass.getSuperclass() == null) {
                continue;
            }

            if (aClass.getInterfaces().length > 0) {
                continue;
            }

            if (!"java.lang.Object".equals(aClass.getSuperclass().getCanonicalName())) {
                continue;
            }

            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                matcher = pattern.matcher(method.getName());
                if (!matcher.matches()) {
                    continue next;
                }
            }

            Constructor[] constructors = aClass.getConstructors();
            if (constructors.length > 1) {
                continue;
            } else if (constructors.length == 1) {
                if (constructors[0].getParameterTypes().length > 0) {
                    continue;
                }
            }

            pojos.add(aClass);
        }

        StringBuilder s = new StringBuilder("POJO: ");
        for (int i = 0; i < pojos.size() - 1; i++) {
            s.append(pojos.get(i).getName() + ", ");
        }
        if (pojos.size() > 0) {
            s.append(pojos.get(pojos.size() - 1));
        }
        return s.toString();
    }
}
