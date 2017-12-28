package ru.itis.inform.checkers.classcheckers;

import ru.itis.inform.checkers.EntityCheckerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class EntityFieldChecker implements ClassChecker {
    public String start(ArrayList<Class> classes) {
        List<Class> entityClasses = new ArrayList<>();
        for (Class aClass : classes) {
            if (EntityCheckerHelper.getInstance().isEntity(aClass)) {
                entityClasses.add(aClass);
                if (aClass.getDeclaredFields().length < 3) {
                    return new StringBuilder("Сущность: ")
                            .append(aClass.getName())
                            .append(" содержит только ")
                            .append(aClass.getDeclaredFields().length)
                            .append(" поля")
                            .toString();
                }
            }
        }
        StringBuilder builder = new StringBuilder("Сущности: ");

        for (Class entityClass : entityClasses) {
            builder.append(entityClass.getName()).append(",\n");
        }
        if (entityClasses.size() > 0) {
            builder.append("Количество сущностей: ")
                    .append(entityClasses.size())
                    .append("\n")
                    .append("Они содержат более 3-х полей");
        }

        return builder.toString();
    }
}
