package ru.itis.inform.checkers.classcheckers;

import ru.itis.inform.checkers.Checker;
import ru.itis.inform.checkers.EntityCheckerHelper;

import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class ORMChecker implements Checker {
    public String start(ArrayList<Class> classes) {
        int count = 0;
        for (Class curClass : classes) {
            if (EntityCheckerHelper.getInstance().isEntity(curClass)) {
                count++;
            }
        }
        return ("Количество ORM-классов в проекте: " + count);
    }
}
