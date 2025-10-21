package org.bubblesort.bubblesort;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class BubbleSortApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 400);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        int[] array = {50, 30, 70, 1, 90, 60, 20}; //Change later so users can input whatever they want
        Button reset = new Button("Reset Animation");
        reset.getStyleClass().add("reset-btn");
        root.getChildren().add(reset);
        BubbleSortController controller = new BubbleSortController(root, array);
        reset.setOnAction(e->{
            controller.resetAnimation(array);
            controller.sort();
        });
        stage.setScene(scene);
        stage.setTitle("Bubble Sort Visualizer");
        //No fullscreen for now, will change later
        stage.maximizedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(t1){
                stage.setMaximized(false);
            }
        }));
        stage.show();
        //Maybe add some bar to adjust the speed
        controller.sort();
    }
    public static void main(String[] args) { launch(); }
}
