package org.mergesort.mergesort;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class MergeSortController {
    private final HBox container;
    private final List<Bar> bars;
    private final double spacing = 10;
    private double speedMultiplier = 1.0;
    private final double baseAnimationSpeed = 900;
    public MergeSortController(HBox container, int[] array) {
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
            if (val > maxValue) maxValue = val;
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
        int n = bars.size();
        int[] aux = new int[n];
        for (int width = 1; width < n; width *= 2) {
            for (int i = 0; i < n; i += 2 * width) {
                int left = i;
                int mid = Math.min(i + width, n);
                int right = Math.min(i + 2 * width, n);
                mainSequence.getChildren().add(createMergeAnimation(left, mid, right));
            }
        }
        mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
            for (Bar bar : bars) bar.markSorted();
        }));

        mainSequence.play();
    }
    private SequentialTransition createMergeAnimation(int left, int mid, int right) {
        SequentialTransition seq = new SequentialTransition();

        List<Bar> leftBars = new ArrayList<>();
        List<Bar> rightBars = new ArrayList<>();
        for (int i = left; i < mid; i++) leftBars.add(bars.get(i));
        for (int i = mid; i < right; i++) rightBars.add(bars.get(i));
        int l = 0, r = 0;
        List<Bar> merged = new ArrayList<>();

        while (l < leftBars.size() || r < rightBars.size()) {
            final int li = l, ri = r;
            seq.getChildren().add(createPause(getAnimationDuration(), () -> {
                bars.subList(left, right).forEach(Bar::removeHighlight);
                if (li<leftBars.size()) leftBars.get(li).highlightLeft();
                if (ri<rightBars.size()) rightBars.get(ri).highlightRight();
                if (li<leftBars.size() && ri < rightBars.size()) {
                    leftBars.get(li).highlightCompare();
                    rightBars.get(ri).highlightCompare();
                }
            }));
            if (l<leftBars.size() && (r>=rightBars.size() || leftBars.get(l).getValue()<=rightBars.get(r).getValue())) {
                merged.add(leftBars.get(l));
                l++;
            }
            else {
                merged.add(rightBars.get(r));
                r++;
            }
        }
        for (int i = 0; i < merged.size(); i++) {
            Bar bar = merged.get(i);
            int finalIndex = left + i;
            int currentIndex = bars.indexOf(bar);
            double distance = (finalIndex - currentIndex) * (bar.getBoundsInParent().getWidth() + spacing);

            TranslateTransition move = new TranslateTransition(Duration.millis(getAnimationDuration()), bar);
            move.setByX(distance);
            seq.getChildren().add(createPause(getAnimationDuration(), move::play));
        }
        seq.getChildren().add(createPause(getAnimationDuration(), () -> {
            for (int i = 0; i < merged.size(); i++) {
                bars.set(left + i, merged.get(i));
                merged.get(i).removeHighlight();
                merged.get(i).markSorted();
            }
        }));

        seq.getChildren().add(createPause(getAnimationDuration(), null));
        return seq;
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