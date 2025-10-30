package org.selectionsort.selectionsort;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SelectionSortControllerTest {
    @BeforeAll
    static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }
    @Test
    void testCreateBarsAndResetAnimation() throws InterruptedException {
        HBox container = new HBox();
        int[] array = {4, 2, 6};
        SelectionSortController controller = new SelectionSortController(container, array);
        assertEquals(array.length, container.getChildren().size(), "Container should contain all bars!");
        for (int i = 0; i < array.length; i++) {
            Bar bar = (Bar) container.getChildren().get(i);
            assertEquals(array[i], bar.getValue(), "Bar value should match array element!");
        }
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.resetAnimation(array);
            latch.countDown();
        });
        latch.await(500, TimeUnit.MILLISECONDS);

        assertEquals(array.length, container.getChildren().size(), "After reset, container should match array length!");
        for (int i = 0; i < array.length; i++) {
            Bar bar = (Bar) container.getChildren().get(i);
            assertEquals(array[i], bar.getValue(), "After reset, bar value should match array element!");
        }
    }
    @Test
    void testSortDoesNotChangeNumberOfBars() throws InterruptedException {
        HBox container = new HBox();
        int[] array = {3, 1, 2};
        SelectionSortController controller = new SelectionSortController(container, array);

        int initialCount = container.getChildren().size();

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.sort();
            latch.countDown();
        });
        latch.await(1, TimeUnit.SECONDS);

        assertEquals(initialCount, container.getChildren().size(), "Number of bars should not change after sort animation!");
    }
    @Test
    void testSpeedMultiplierSetterGetter() {
        HBox container = new HBox();
        int[] array = {1, 2, 3};
        SelectionSortController controller = new SelectionSortController(container, array);

        controller.setSpeedMultiplier(2.5);
        assertEquals(2.5, controller.getSpeedMultiplier(), "Speed multiplier getter/setter should work correctly");
    }
}