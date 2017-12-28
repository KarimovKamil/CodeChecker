package ru.itis.inform.checkers.classcheckers;

import ru.itis.inform.checkers.Checker;
import ru.itis.inform.checkers.EntityCheckerHelper;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class EntityFieldChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        List<Class> entityClasses = new ArrayList<>();
        for (Class aClass : classes) {
            if (EntityCheckerHelper.getInstance().isEntity(aClass)) {
                entityClasses.add(aClass);
                if (aClass.getDeclaredFields().length < 3) {
                    return new StringBuilder("Entity class: ")
                            .append(aClass.getName())
                            .append(" contains only ")
                            .append(aClass.getDeclaredFields().length)
                            .append(" fields")
                            .toString();
                }
            }
        }
        StringBuilder builder = new StringBuilder("Entity classes: ");

        for (Class entityClass : entityClasses) {
            builder.append(entityClass.getName()).append(",\n");
        }
        if (entityClasses.size() > 0) {
            builder.append("Entity classes amount is ")
                    .append(entityClasses.size())
                    .append("\n")
                    .append("They contain more than 3 field");
        }

        return builder.toString();
    }
}
