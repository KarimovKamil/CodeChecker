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

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                ArrayList<Class> list = getClassesInPackage(pkg + "." + files[i].getName());
                for (int j = 0; j < list.size(); j++) {
                    classes.add(list.get(j));
                }
            } else {
                try {
                    classes.add(Class.forName(pkg + "." + files[i].getName()
                            .substring(0, files[i].getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }
}
