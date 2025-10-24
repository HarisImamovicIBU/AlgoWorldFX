package org.bubblesort.bubblesort;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import java.awt.*;
import javafx.scene.shape.Rectangle;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class BarTest {
    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});
    }
    @Test
    void testValueIsSetCorrectly(){
        Bar bar = new Bar(10, 50, 20);
        assertEquals(10, bar.getValue(), "Bar value should match the constructor value!");
    }
    @Test
    void testInitialColor() throws NoSuchFieldException, IllegalAccessException {
        Bar bar = new Bar(10, 50, 20);
        Field rectField = Bar.class.getDeclaredField("rect");
        rectField.setAccessible(true);
        Rectangle rect = (Rectangle) rectField.get(bar);
        assertEquals(Color.web("#0d6efd"), rect.getFill(), "Initial color should be blue!");
    }
    @Test
    void testHighlightChangesColor() throws NoSuchFieldException, IllegalAccessException {
        Bar bar = new Bar(10, 50, 20);
        bar.highlight();
        Field rectField = Bar.class.getDeclaredField("rect");
        rectField.setAccessible(true);
        Rectangle rect = (Rectangle) rectField.get(bar);
        assertEquals(Color.web("#dc3545"), rect.getFill(), "Highlight should change color to red!");
    }
    @Test
    void testRemoveHighlightRestoresColor() throws NoSuchFieldException, IllegalAccessException {
        Bar bar = new Bar(10, 50, 20);
        bar.highlight();
        bar.removeHighlight();
        Field rectField = Bar.class.getDeclaredField("rect");
        rectField.setAccessible(true);
        Rectangle rect = (Rectangle) rectField.get(bar);
        assertEquals(Color.web("#0d6efd"), rect.getFill(), "Removing highlight should restore original color!");
    }
}