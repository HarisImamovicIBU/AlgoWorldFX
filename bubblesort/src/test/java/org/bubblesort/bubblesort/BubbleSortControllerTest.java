package org.bubblesort.bubblesort;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortControllerTest {
    @BeforeAll
    static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }
    @Test
    void testCreateBarsAndResetAnimation() throws InterruptedException {
        HBox container = new HBox();
        int[] array = {5, 3, 7};
        BubbleSortController controller = new BubbleSortController(container, array);
        assertEquals(array.length, container.getChildren().size(), "Container should contain all bars!");
        for (int i = 0; i < array.length; i++) {
            Bar bar = (Bar) container.getChildren().get(i);
            assertEquals(array[i], bar.getValue(), "Bar value should match array element!");
        }
        Platform.runLater(() -> controller.resetAnimation(array));
        Thread.sleep(200); //Allow FX thread to update

        assertEquals(array.length, container.getChildren().size(), "After reset, container should match new(old) array length!");
        for (int i = 0; i < array.length; i++) {
            Bar bar = (Bar) container.getChildren().get(i);
            assertEquals(array[i], bar.getValue(), "After reset, bar value should match new(old) array element!");
        }
    }
    @Test
    void testSwapAnimationDoesNotChangeNumberOfBars() throws InterruptedException {
        HBox container = new HBox();
        int[] array = {1, 2, 3};
        BubbleSortController controller = new BubbleSortController(container, array);

        int initialCount = container.getChildren().size();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.sort();
            latch.countDown();
        });
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(initialCount, container.getChildren().size(), "Number of bars should not change after sort animation!");
    }
}
