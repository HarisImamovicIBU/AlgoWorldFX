package org.mergesort.mergesort;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class MergeSortApp extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        HBox barsContainer = new HBox();
        barsContainer.setAlignment(Pos.BOTTOM_CENTER);
        barsContainer.setSpacing(10);

        Label speedLabel = new Label("Speed: 1.0x");
        speedLabel.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #495057;
        """);

        Slider speedSlider = new Slider(0, 5, 2);
        speedSlider.setMajorTickUnit(1);
        speedSlider.setMinorTickCount(0);
        speedSlider.setSnapToTicks(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(false);
        speedSlider.setPrefWidth(300);
        speedSlider.getStyleClass().add("speed-slider");

        double[] speeds = {0.25, 0.5, 1.0, 1.25, 1.5, 2.0};

        HBox speedLabels = new HBox();
        speedLabels.setAlignment(Pos.CENTER);
        speedLabels.setPrefWidth(300);
        speedLabels.setSpacing(0);

        for (double speed : speeds) {
            Label tickLabel = new Label(speed + "x");
            tickLabel.setStyle("""
                -fx-font-size: 10px;
                -fx-text-fill: #6c757d;
            """);
            tickLabel.setPrefWidth(300.0 / speeds.length);
            tickLabel.setAlignment(Pos.CENTER);
            speedLabels.getChildren().add(tickLabel);
        }

        VBox speedControl = new VBox(8);
        speedControl.setAlignment(Pos.CENTER);
        speedControl.getChildren().addAll(speedLabel, speedSlider, speedLabels);
        speedControl.setPadding(new Insets(10, 20, 10, 20));
        speedControl.setStyle("""
            -fx-background-color: #ffffff;
            -fx-border-color: #dee2e6;
            -fx-border-width: 1;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """);

        Button reset = new Button("Reset Animation");
        reset.getStyleClass().add("reset-btn");

        HBox controlsBox = new HBox(30);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.getChildren().addAll(speedControl, reset);
        controlsBox.setStyle("""
            -fx-padding: 20;
            -fx-border-color: #cccccc;
            -fx-border-width: 2 0 0 0;
            -fx-border-style: solid;
            -fx-background-color: #f8f9fa;
        """);

        root.setCenter(barsContainer);
        root.setBottom(controlsBox);

        Scene scene = new Scene(root, 900, 550);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        int[] array = {50, 30, 70, 5, 90, 60, 20, 20, 10, 45};
        MergeSortController controller = new MergeSortController(barsContainer, array);

        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            double speedMultiplier = speeds[index];
            controller.setSpeedMultiplier(speedMultiplier);
            speedLabel.setText("Speed: " + speedMultiplier + "x");
        });

        reset.setOnAction(e -> {
            controller.resetAnimation(array);
            controller.sort();
        });

        stage.setScene(scene);
        stage.setTitle("Merge Sort Visualizer");
        stage.show();

        controller.sort();
    }
    public static void main(String[] args) {launch();}
}