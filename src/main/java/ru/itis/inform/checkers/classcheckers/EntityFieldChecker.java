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
                    return "Сущность: " +
                            aClass.getName() +
                            " содержит только " +
                            aClass.getDeclaredFields().length +
                            " поля";
                }
            }
        }
        StringBuilder builder = new StringBuilder();

        if (entityClasses.size() > 0) {
            builder.append("Все сущности  содержат 3 или более полей. " +
                    "Количество сущностей, содержащих более 2-х полей: ")
                    .append(entityClasses.size());
        }

        return builder.toString();
    }
}
