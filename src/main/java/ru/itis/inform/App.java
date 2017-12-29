package ru.itis.inform;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        tasks = new String[]{". Каждая Entity должна содержать не менее трех полей ",
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

        ClassChecker[] classCheckers = new ClassChecker[]{
                new EntityFieldChecker(),
                new HashExistenceChecker(),
                new MappingChecker(),
                new ORMChecker(),
                new PackageMappingChecker(),
                new POJOChecker(),
                new RepositoryChecker(),
                new SpringSecurityExistenceChecker()
        };

        ClassChecker[] testCheckers = new ClassChecker[]{
                new MockChecker(),
                new UnitTestChecker()
        };

        FileChecker[] fileCheckers = new FileChecker[]{
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
                File cl = new File(archive.toString() + "\\target\\classes");
                File te = new File(archive.toString() + "\\target\\test-classes");
                ArrayList<Class> classes = classReader.getClassesInPackage(cl);
                ArrayList<Class> tests = classReader.getClassesInPackage(te);
                ArrayList<String> output = new ArrayList<>();
                for (int i = 0; i < classCheckers.length; i++) {
                    if (tasksBool[i]) {
                        output.add(classCheckers[i].start(classes));
                    }
                }

                for (int i = classCheckers.length; i < tasksBool.length - fileCheckers.length; i++) {
                    if (tasksBool[i]) {
                        output.add(testCheckers[i - classCheckers.length].start(tests));
                    }
                }

                for (int i = classCheckers.length + testCheckers.length; i < tasksBool.length; i++) {
                    if (tasksBool[i]) {
                        output.add(fileCheckers[i - classCheckers.length - testCheckers.length].start(archive.toString()));
                    }
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Summary");
                alert.setHeaderText(null);
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < output.size(); i++) {
                    result.append(i + 1);
                    result.append(output.get(i)).append("\n");

                }
                alert.setContentText(result.toString());
                alert.showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        tf.setMinSize(392, 20);
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

        CheckBox selectAll = new CheckBox();
        selectAll.setSelected(true);
        selectAll.setText("All");
        selectAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                for (int i = 0; i < tasks.length; i++) {
                    tasksBool[i] = new_val;
                    cbs[i].setSelected(new_val);
                }
            }
        });

        HBox hBox1 = new HBox();
        hBox1.setSpacing(5);
        hBox1.getChildren().addAll(tf, selectAll, btn);

        vbox.getChildren().add(hBox1);

        HBox hBox[] = new HBox[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            hBox[i] = new HBox();
            tasksF[i] = new TextField();
            tasksF[i].setText((i + 1) + tasks[i]);
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
