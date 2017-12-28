package ru.itis.inform;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class ClassReader {

    public ArrayList<Class> getClassesInPackage(File dir) {
        ArrayList<Class> classes = new ArrayList<>();
        for (File file : dir.listFiles()) {
            HashSet<String> ser = find(file);
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

    private HashSet<String> find(File file) {
        HashSet<String> setOfClasses = new HashSet<>();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                HashSet<String> set = find(child);
                for (String s : set) {
                    setOfClasses.add(s);
                }
            }
        } else if (file.getName().endsWith(".java")) {
            String[] split = file.getAbsolutePath().split("java\\\\");
            setOfClasses.add(split[1].replace('\\', '.')
                    .substring(0, split[1].length() - 5));
        }
        return setOfClasses;
    }
}
