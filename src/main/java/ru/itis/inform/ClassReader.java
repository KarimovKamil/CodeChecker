package ru.itis.inform;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */

import java.io.File;
import java.util.ArrayList;

public class ClassReader {

    public ArrayList<Class> getClassesInPackage(String pkg) {
        ArrayList<Class> classes = new ArrayList<>();
        File dir = new File(Thread.currentThread().getContextClassLoader()
                .getResource(pkg.replace('.', '/')).getFile());
        File[] files = dir.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    ArrayList<Class> list = getClassesInPackage(pkg + "." + files[i].getName());
                    classes.addAll(list);
                } else {
                    try {
                        classes.add(Class.forName(pkg + "." + files[i].getName()
                                .substring(0, files[i].getName().length() - 6)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }
}
