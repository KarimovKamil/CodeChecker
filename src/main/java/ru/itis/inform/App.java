package ru.itis.inform;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Kamil Karimov on 28.12.2017.
 */
public class App extends Application {
    private File archive = null;
    private String tasks[];
    private boolean tasksBool[];

    @Override
    public void start(final Stage stage) {
        tasks = new String[]{"1. Определенное количество ORM-классов @Entity ",
                "2. Класс является POJO",
                "3. В проекте использован JPARepository или CrudRepository ",
                "4. Подсчитать количество юнит-тестов в проекте ",
                "5. Проверить наличие в тестах Mock-объектов библиотеки Mockito ",
                "6. В проекте использован/не использован - FreeMarker, JSP, JSTL ",
                "7. В проекте реализовано хэширование паролей ",
                "8. Проект использует Spring Security ",
                "9. Каждая Entity должна содержать не менее трех полей ",
                "10. В приложении есть N разных ссылок на страницы приложения (по маппингам) ",
                "11. Проект имеет структуру Maven-проекта ",
                "12. В проекте все классы лежат в своих пакетах (Services, Controllers, DAO/Repository)"};

        final TextField[] tasksF = new TextField[tasks.length];
        final CheckBox[] cbs = new CheckBox[tasks.length];
        final TextField tf = new TextField("Hello! Choose a folder you want to check.");
        final Button startBtn = new Button();

        startBtn.setText("Start!");
        startBtn.setMinSize(520, 25);
        startBtn.setDisable(true);
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Hello");
                } catch (Exception ex) {
                    System.out.println("Exception!");
                }
            }
        });

        tf.setMinSize(437, 20);
        tf.setDisable(true);

        final DirectoryChooser fileChooser = new DirectoryChooser();
        Button btn = new Button();
        btn.setText("Browse files");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showDialog(stage);
                if (file != null) {
                    startBtn.setDisable(false);
                    archive = file;
                    tf.setText(archive.toString());
                    System.out.println(archive.toString());
                }
            }
        });

        tasksBool = new boolean[tasksF.length];

        for (int i = 0; i < tasksF.length; i++) {
            tasksBool[i] = true;
            cbs[i] = new CheckBox();
            cbs[i].setId(Integer.toString(i));
            cbs[i].setSelected(tasksBool[i]);
            final CheckBox cb = cbs[i];
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                                    Boolean old_val, Boolean new_val) {
                    int id = Integer.valueOf(cb.getId());
                    tasksBool[id] = new_val;
                }
            });
        }

        VBox vbox = new VBox();

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(tf, btn);

        vbox.getChildren().add(startBtn);
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
