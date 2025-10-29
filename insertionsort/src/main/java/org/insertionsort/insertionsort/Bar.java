package org.insertionsort.insertionsort;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Bar extends StackPane {
    private final int value;
    private final Rectangle rect;
    private final Label label;
    public Bar(int value, double width, double maxValue) {
        this.value = value;
        double minHeight = 50;
        double height = minHeight + (value / (double) maxValue) * 250;

        rect = new Rectangle(width, height);
        rect.setFill(Color.web("#0d6efd"));
        rect.setStroke(Color.WHITE);

        label = new Label(String.valueOf(value));
        label.setTextFill(Color.BLACK);
        label.setFont(new Font("Verdana", 20));

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
    public void highlightKey() {
        rect.setFill(Color.web("#ffc107"));
    }
    public void markSorted() {
        rect.setFill(Color.web("#28a745"));
    }
    public void removeHighlight() {
        rect.setFill(Color.web("#0d6efd"));
    }
}