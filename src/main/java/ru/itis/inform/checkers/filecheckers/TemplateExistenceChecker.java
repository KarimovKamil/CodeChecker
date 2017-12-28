package ru.itis.inform.checkers.filecheckers;

import org.apache.commons.io.FilenameUtils;
import ru.itis.inform.checkers.Checker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Yoko on 27.12.2017.
 */
public class TemplateExistenceChecker implements Checker {
    String FILENAME = "";
    boolean ftl = false;
    boolean jsp = false;
    boolean jstl = false;

    public TemplateExistenceChecker(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    public String start(ArrayList<Class> classes) {
        final File folder = new File(FILENAME);
        listFilesForFolder(folder);
        return getResult();
    }

    private void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                String ext = FilenameUtils.getExtension(fileEntry.getName());
                if ("ftl".equals(ext)) {
                    ftl = true;
                } else {
                    if ("jsp".equals(ext)) {
                        jsp = true;
                        checkJstl(fileEntry.toString());
                    }
                }
            }
        }
    }

    private void checkJstl(String filename) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<%@ taglib")
                        && line.contains("uri=\"http://java.sun.com/jsp/jstl/")) {
                    jstl = true;
                }
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private String getResult() {
        StringBuilder result = new StringBuilder("В проекте используется ");
        if (ftl) result.append("FreeMarker ");
        if (jsp) {
            result.append("JSP ");
            if (jstl) {
                result.append("JSTL ");
            }
        }
        if (!ftl && !jsp && !jstl) {
            result = new StringBuilder("В проекте ни используется ни FreeMarker, ни JSP, ни JSTL");
        }
        return result.toString();
    }

}
