package ru.itis.inform.checkers.filecheckers;


import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Kamil Karimov on 26.12.2017.
 */
public class MavenStructureExistenceChecker implements FileChecker {

    private static final String POM = "pom.xml";
    private static final String SRC = "src";
    private static final String MAIN = "main";
    private static final String TEST = "test";
    private static final String IT = "it";
    private static final String SITE = "site";
    private static final String ASSEMBLY = "assembly";

    private static final String LICENSE = "LICENSE.txt";
    private static final String NOTICE = "NOTICE.txt";
    private static final String README = "README.txt";

    private static final String TARGET = "target";

    private String[] mainDir = {"java", "resources", "config", "scripts", "webapp"};
    private String[] testDir = {"java", "resources"};

    private String userDir;
    private Set<String> packages = new TreeSet<>();

    public String start(String projectPath) {
        this.userDir = projectPath;
        File project = new File(userDir);

        getPackages(project);
        if (!packages.contains(POM) || !packages.contains(SRC)) {
            return "Проект не имеет структуру Maven-проекта ";
        }

        StringBuilder builder = new StringBuilder("Проект имеет структуру Maven-проекта . Проект содержит:\n ");

        for (String path : packages) {
            builder.append(userDir).append("/").append(path).append(",\n");
        }

        return builder.toString();
    }

    private void getPackages(File project) {
        if (!project.isDirectory()) return;

        for (File file : project.listFiles()) {
            if (file.isDirectory()) {

                if (file.getName().equals(SRC)) {
                    packages.add(SRC);

                    for (File srcFile : file.listFiles()) {
                        if (!srcFile.isDirectory())
                            continue;
                        switch (srcFile.getName()) {
                            case MAIN: {
                                addFilesFromDir(srcFile, MAIN);
                                packages.add(formSrcName(MAIN));
                                break;
                            }
                            case TEST: {
                                addFilesFromDir(srcFile, TEST);
                                packages.add(formSrcName(TEST));
                                break;
                            }
                            case SITE: {
                                packages.add(formSrcName(SITE));
                                break;
                            }
                            case ASSEMBLY: {
                                packages.add(formSrcName(ASSEMBLY));
                                break;
                            }
                            case IT: {
                                packages.add(formSrcName(IT));
                                break;
                            }
                        }
                    }
                } else if (file.getName().equals(TARGET)) {
                    packages.add(TARGET);
                }
            } else if (!file.isDirectory()) {

                switch (file.getName()) {
                    case POM: {
                        packages.add(POM);
                        break;
                    }
                    case NOTICE: {
                        packages.add(NOTICE);
                        break;
                    }
                    case LICENSE: {
                        packages.add(LICENSE);
                        break;
                    }
                    case README: {
                        packages.add(README);
                        break;
                    }
                }
            }
        }
    }

    private String formSrcName(String name) {
        return SRC + "/" + name;
    }

    private void addFilesFromDir(File file, String name) {
        List<String> childSDirs;

        if (name.equals(TEST)) {
            childSDirs = Arrays.asList(testDir);
        } else childSDirs = Arrays.asList(mainDir);

        for (File child : file.listFiles()) {
            if (!child.isDirectory()) continue;

            if (childSDirs.contains(child.getName())) {
                packages.add(formSrcName(file.getName()) + "/" + child.getName());
            }
        }
    }

}
