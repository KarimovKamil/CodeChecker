package ru.itis.inform;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class ClassReader {

    public ArrayList<Class> getClassesInPackage(String pkg) {
        ArrayList<Class> classes = new ArrayList<>();
        File dir = new File(Thread.currentThread().getContextClassLoader()
                .getResource(pkg.replace('.', '/')).getFile());
        for (File file : dir.listFiles()) {
            HashSet<String> ser = find(pkg, file);
            for (String s : ser) {
                try {
                    classes.add(Class.forName(s));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    private HashSet<String> find(String pkg, File file) {
        HashSet<String> setOfClasses = new HashSet<>();
        String resource = pkg + "." + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                HashSet<String> set = find(pkg + "." + file.getName(), child);
                for (String s : set) {
                    setOfClasses.add(s);
                }
            }
        } else {
            if (resource.indexOf('$') == -1) {
                setOfClasses.add(pkg + "." + file.getName().substring(0, file.getName().length() - 6));
            }
        }
        return setOfClasses;
    }
}
