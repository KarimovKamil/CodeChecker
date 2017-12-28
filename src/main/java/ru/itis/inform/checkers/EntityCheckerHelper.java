package ru.itis.inform.checkers;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;

/**
 * Created by Kamil Karimov on 28.12.2017.
 */
public class EntityCheckerHelper {
    private static volatile EntityCheckerHelper instance;

    public static EntityCheckerHelper getInstance() {
        EntityCheckerHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (EntityCheckerHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EntityCheckerHelper();
                }
            }
        }
        return localInstance;
    }

    public boolean isEntity(Class aClass) {
        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Entity) {
                return true;
            }
        }
        return false;
    }
}
