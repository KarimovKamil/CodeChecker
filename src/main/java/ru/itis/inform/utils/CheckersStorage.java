package ru.itis.inform.utils;

import ru.itis.inform.checkers.classcheckers.*;
import ru.itis.inform.checkers.filecheckers.FileChecker;
import ru.itis.inform.checkers.filecheckers.MavenStructureExistenceChecker;
import ru.itis.inform.checkers.filecheckers.TemplateExistenceChecker;
import ru.itis.inform.checkers.testcheckers.MockChecker;
import ru.itis.inform.checkers.testcheckers.UnitTestChecker;

/**
 * Created by Kamil Karimov on 29.12.2017.
 */
public class CheckersStorage {
    public static String[] checkList = new String[]{". Каждая Entity должна содержать не менее трех полей ",
            ". В проекте реализовано хэширование паролей ",
            ". В приложении есть N разных ссылок на страницы приложения (по маппингам) ",
            ". Определенное количество ORM-классов @Entity ",
            ". В проекте все классы лежат в своих пакетах (Services, Controllers, DAO/Repository)",
            ". Класс является POJO",
            ". В проекте использован JPARepository или CrudRepository ",
            ". Проект использует Spring Security ",
            ". Проверить наличие в тестах Mock-объектов библиотеки Mockito ",
            ". Подсчитать количество юнит-тестов в проекте ",
            ". Проект имеет структуру Maven-проекта ",
            ". В проекте использован/не использован - FreeMarker, JSP, JSTL "
    };

    public static ClassChecker[] classCheckers = new ClassChecker[]{
            new EntityFieldChecker(),
            new HashExistenceChecker(),
            new MappingChecker(),
            new ORMChecker(),
            new PackageMappingChecker(),
            new POJOChecker(),
            new RepositoryChecker(),
            new SpringSecurityExistenceChecker()
    };

    public static ClassChecker[] testCheckers = new ClassChecker[]{
            new MockChecker(),
            new UnitTestChecker()
    };

    public static FileChecker[] fileCheckers = new FileChecker[]{
            new MavenStructureExistenceChecker(),
            new TemplateExistenceChecker()
    };
}
