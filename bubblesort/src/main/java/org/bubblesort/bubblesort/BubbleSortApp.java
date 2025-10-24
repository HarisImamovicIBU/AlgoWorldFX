package org.bubblesort.bubblesort;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class BubbleSortApp extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        HBox barsContainer = new HBox();
        barsContainer.setAlignment(Pos.BOTTOM_CENTER);
        barsContainer.setSpacing(10);
        Button reset = new Button("Reset Animation");
        reset.getStyleClass().add("reset-btn");
        HBox buttonBox = new HBox(reset);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("""
            -fx-padding: 20;
            -fx-border-color: #cccccc;
            -fx-border-width: 2 0 0 0;
            -fx-border-style: solid;
            -fx-background-color: #f8f9fa;
        """);
        root.setCenter(barsContainer);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        int[] array = {50, 30, 70, 5, 90, 60, 20, 20, 10, 45};
        BubbleSortController controller = new BubbleSortController(barsContainer, array);
        reset.setOnAction(e->{
            controller.resetAnimation(array);
            controller.sort();
        });

        stage.setScene(scene);
        stage.setTitle("Bubble Sort Visualizer");
        stage.show();
        controller.sort();
    }
    public static void main(String[] args) { launch(); }
}