package org.bubblesort.bubblesort;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class BubbleSortController {
    private final HBox container;
    private final List<Bar> bars;
    private final double spacing = 10;
    private double speedMultiplier = 1.0;
    private final double baseAnimationSpeed = 900;
    public BubbleSortController(HBox container, int[] array) {
        this.container = container;
        this.bars = new ArrayList<>();
        container.setSpacing(spacing);
        createBars(array);
    }
    public void setSpeedMultiplier(double multiplier) {
        this.speedMultiplier = multiplier;
    }
    public double getSpeedMultiplier() {
        return speedMultiplier;
    }
    private double getAnimationDuration() {
        return baseAnimationSpeed / speedMultiplier;
    }
    private void createBars(int[] array) {
        container.getChildren().clear();
        int maxValue = 0;
        for (int val : array) {
            if (val>maxValue){
                maxValue = val;
            }
        }
        double barWidth = 50;
        for (int val : array) {
            Bar bar = new Bar(val, barWidth, maxValue);
            bars.add(bar);
            container.getChildren().add(bar);
        }
    }
    public void sort() {
        SequentialTransition mainSequence = new SequentialTransition();
        for (int i = 0; i < bars.size() - 1; i++) {
            for (int j = 0; j < bars.size() - i - 1; j++) {
                Bar bar1 = bars.get(j);
                Bar bar2 = bars.get(j + 1);
                mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
                    bar1.highlight();
                    bar2.highlight();
                }));
                if (bar1.getValue() > bar2.getValue()) {
                    mainSequence.getChildren().add(createSwapAnimation(j, j + 1));
                    bars.set(j, bar2);
                    bars.set(j + 1, bar1);
                }
                else {
                    mainSequence.getChildren().add(createPause(getAnimationDuration(), null));
                }
                mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
                    bar1.removeHighlight();
                    bar2.removeHighlight();
                }));
            }
        }
        mainSequence.play();
    }
    private SequentialTransition createSwapAnimation(int index1, int index2) {
        Bar bar1 = bars.get(index1);
        Bar bar2 = bars.get(index2);
        double distance = bar1.getBoundsInParent().getWidth() + spacing;
        TranslateTransition move1 = new TranslateTransition(Duration.millis(getAnimationDuration()), bar1);
        move1.setByX(distance);
        TranslateTransition move2 = new TranslateTransition(Duration.millis(getAnimationDuration()), bar2);
        move2.setByX(-distance);
        SequentialTransition swapSeq = new SequentialTransition();
        PauseTransition startPause = new PauseTransition(Duration.millis(getAnimationDuration()));
        startPause.setOnFinished(e -> {
            move1.play();
            move2.play();
        });
        swapSeq.getChildren().add(startPause);
        swapSeq.getChildren().add(new PauseTransition(Duration.millis(getAnimationDuration())));
        return swapSeq;
    }
    private PauseTransition createPause(double millis, Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.millis(millis));
        if (action != null) pause.setOnFinished(e -> action.run());
        return pause;
    }
    public void resetAnimation(int[] array) {
        bars.clear();
        createBars(array);
    }
}