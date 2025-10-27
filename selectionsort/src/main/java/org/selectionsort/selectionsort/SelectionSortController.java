package org.selectionsort.selectionsort;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SelectionSortController {
    @FXML
    private Label welcomeText;

    public SelectionSortController(HBox barsContainer, int[] array) {
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void resetAnimation(int[] array) {
    }

    public void sort() {
    }
}