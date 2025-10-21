package org.bubblesort.bubblesort;

import javafx.application.Application;
import javafx.scene.Scene;
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
        int[] array = {50, 30, 70, 10, 90, 60, 20}; //Change so user can input whatever they want
        BubbleSortController controller = new BubbleSortController(root, array);

        stage.setScene(scene);
        stage.setTitle("Bubble Sort Visualizer");
        stage.show();
        //Create a reset button to reset the animation
        //Maybe add some bar to adjust the speed
        controller.sort();
    }
    public static void main(String[] args) { launch(); }
}
