package org.bubblesort.bubblesort;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends StackPane {
    private final int value;
    private final Rectangle rect;
    private final Label label;
    public Bar(int value, double width, double maxValue) {
        this.value = value;
        double height = value / (double) maxValue * 300;
        rect = new Rectangle(width, height);
        rect.setFill(Color.web("#0d6efd"));
        rect.setStroke(Color.WHITE);
        label = new Label(String.valueOf(value));
        label.setTextFill(Color.WHITE);
        setAlignment(Pos.BOTTOM_CENTER);
        getChildren().addAll(rect, label);
        setPrefHeight(300);
    }
    public int getValue() {
        return value;
    }
    public void highlight() {
        rect.setFill(Color.web("#dc3545"));
    }
    public void removeHighlight() {
        rect.setFill(Color.web("#0d6efd"));
    }
}
