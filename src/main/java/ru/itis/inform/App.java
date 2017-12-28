package ru.itis.inform;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.itis.inform.checkers.classcheckers.ClassChecker;
import ru.itis.inform.checkers.classcheckers.*;
import ru.itis.inform.checkers.filecheckers.FileChecker;
import ru.itis.inform.checkers.filecheckers.MavenStructureExistenceChecker;
import ru.itis.inform.checkers.filecheckers.TemplateExistenceChecker;
import ru.itis.inform.checkers.testcheckers.MockChecker;
import ru.itis.inform.checkers.testcheckers.UnitTestChecker;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kamil Karimov on 28.12.2017.
 */
public class App extends Application {
    private File archive = null;
    private String tasks[];
    private boolean tasksBool[];

    @Override
    public void start(final Stage stage) {
        tasks = new String[]{"1. Каждая Entity должна содержать не менее трех полей ",
                "2. В проекте реализовано хэширование паролей ",
                "3. В приложении есть N разных ссылок на страницы приложения (по маппингам) ",
                "4. Определенное количество ORM-классов @Entity ",
                "5. В проекте все классы лежат в своих пакетах (Services, Controllers, DAO/Repository)",
                "6. Класс является POJO",
                "7. В проекте использован JPARepository или CrudRepository ",
                "8. Проект использует Spring Security ",
                "9. Проверить наличие в тестах Mock-объектов библиотеки Mockito ",
                "10. Подсчитать количество юнит-тестов в проекте ",
                "11. Проект имеет структуру Maven-проекта ",
                "12. В проекте использован/не использован - FreeMarker, JSP, JSTL "
        };

        ClassChecker[] classCheckers = new ClassChecker[] {
                new EntityFieldChecker(),
                new HashExistenceChecker(),
                new HashExistenceChecker(),
                new MappingChecker(),
                new ORMChecker(),
                new PackageMappingChecker(),
                new POJOChecker(),
                new RepositoryChecker(),
                new SpringSecurityExistenceChecker(),
                new MockChecker(),
                new UnitTestChecker()
        };

        FileChecker[] fileCheckers = new FileChecker[] {
                new MavenStructureExistenceChecker(),
                new TemplateExistenceChecker()
        };

        final TextField[] tasksF = new TextField[tasks.length];
        final CheckBox[] cbs = new CheckBox[tasks.length];
        final TextField tf = new TextField("Hello! Choose a folder you want to check.");
        final Button startBtn = new Button();

        startBtn.setText("Start!");
        startBtn.setMinSize(520, 25);
        startBtn.setDisable(true);
        startBtn.setOnAction(event -> {
            try {
                ClassReader classReader = new ClassReader();
                ArrayList<Class> classes = classReader.getClassesInPackage(archive.toString());
                ArrayList<String> output = new ArrayList<>();
                for (int i = 0; i < classCheckers.length; i++) {
                    if (tasksBool[i]) {
                        output.add(classCheckers[i].start(classes));
                    }
                }

                for (int i = classCheckers.length; i < tasksBool.length; i++) {
                    if (tasksBool[i]) {
                        output.add(fileCheckers[i].start(archive.toString()));
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Summary");
                alert.setHeaderText(null);
                StringBuilder result = new StringBuilder();
                for (String s : output) {
                    result.append(s).append("\n");
                }
                alert.setContentText(result.toString());
                alert.showAndWait();

            } catch (Exception ex) {
                System.out.println("Exception!");
            }
        });

        tf.setMinSize(437, 20);
        tf.setDisable(true);

        final DirectoryChooser fileChooser = new DirectoryChooser();
        Button btn = new Button();
        btn.setText("Browse files");
        btn.setOnAction(event -> {
            File file = fileChooser.showDialog(stage);
            if (file != null) {
                startBtn.setDisable(false);
                archive = file;
                tf.setText(archive.toString());
                System.out.println(archive.toString());
            }
        });

        tasksBool = new boolean[tasksF.length];

        for (int i = 0; i < tasksF.length; i++) {
            tasksBool[i] = true;
            cbs[i] = new CheckBox();
            cbs[i].setId(Integer.toString(i));
            cbs[i].setSelected(tasksBool[i]);
            final CheckBox cb = cbs[i];
            cb.selectedProperty().addListener((ov, old_val, new_val) -> {
                int id = Integer.valueOf(cb.getId());
                tasksBool[id] = new_val;
            });
        }

        VBox vbox = new VBox();

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(tf, btn);

        vbox.getChildren().add(hBox1);

        HBox hBox[] = new HBox[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            hBox[i] = new HBox();
            tasksF[i] = new TextField();
            tasksF[i].setText(tasks[i]);
            tasksF[i].setDisable(true);
            tasksF[i].setMinSize(500, 20);
            hBox[i].getChildren().addAll(tasksF[i], cbs[i]);
            vbox.getChildren().add(hBox[i]);
        }

        vbox.getChildren().add(startBtn);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 520, 300);

        stage.setTitle("Code checker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
