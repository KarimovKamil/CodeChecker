package ru.itis.inform;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */

import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;

public class ClassReader {

    public ArrayList<Class> getClassesInPackage(File dir) {
        URL url = null;
        try {
            url = new URL("file://" + dir.toString().replace('\\', '/') + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(dir.toString().replace('\\', '/') + "/");
        URL[] classUrls = {url};
        URLClassLoader urlClassLoader = new URLClassLoader(classUrls);

        ArrayList<Class> classes = new ArrayList<>();
        for (File file : dir.listFiles()) {
            HashSet<String> ser = find(file);
            for (String s : ser) {
                try {
                    classes.add(urlClassLoader.loadClass(s));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoClassDefFoundError e) {
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
        } else if (file.getName().endsWith(".class")) {
            String[] split = file.getAbsolutePath().split("classes\\\\");
            String resource = split[1].replace('\\', '.')
                    .substring(0, split[1].length() - 6);
            if (resource.indexOf('$') == -1) {
                setOfClasses.add(resource);
            }
        }
        return setOfClasses;
    }
}
