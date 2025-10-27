package org.selectionsort.selectionsort;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class SelectionSortController {
    private final HBox container;
    private final List<Bar> bars;
    private final double spacing = 10;
    public SelectionSortController(HBox container, int[] array) {
        this.container = container;
        this.bars = new ArrayList<>();
        container.setSpacing(spacing);
        createBars(array);
    }
    private void createBars(int[] array) {
        container.getChildren().clear();
        int maxValue = 0;
        for (int val : array) {
            if (val>maxValue) {
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
        List<Bar> sortedBars = new ArrayList<>(bars);
        for (int i = 0; i < sortedBars.size() - 1; i++) {
            int min_idx = i;
            final int currentI = i;
            final Bar currentBar = sortedBars.get(i);
            mainSequence.getChildren().add(createPause(900, () -> {
                currentBar.highlightMin();
            }));
            for (int j = i + 1; j < sortedBars.size(); j++) {
                final int currentJ = j;
                final Bar compareBar = sortedBars.get(j);

                mainSequence.getChildren().add(createPause(900, () -> {
                    compareBar.highlight();
                }));

                if (sortedBars.get(j).getValue() < sortedBars.get(min_idx).getValue()) {
                    final Bar oldMinBar = sortedBars.get(min_idx);
                    min_idx = j;
                    final Bar newMinBar = sortedBars.get(min_idx);

                    mainSequence.getChildren().add(createPause(900, () -> {
                        oldMinBar.removeHighlight();
                        newMinBar.highlightMin();
                    }));
                } else {
                    mainSequence.getChildren().add(createPause(900, () -> {
                        compareBar.removeHighlight();
                    }));
                }
            }
            final int finalMinIdx = min_idx;
            if (finalMinIdx != i) {
                int visualIndex1 = bars.indexOf(sortedBars.get(i));
                int visualIndex2 = bars.indexOf(sortedBars.get(finalMinIdx));
                mainSequence.getChildren().add(createSwapAnimation(visualIndex1, visualIndex2));
                Bar temp = sortedBars.get(i);
                sortedBars.set(i, sortedBars.get(finalMinIdx));
                sortedBars.set(finalMinIdx, temp);

                bars.set(visualIndex1, sortedBars.get(i));
                bars.set(visualIndex2, sortedBars.get(finalMinIdx));
            }
            mainSequence.getChildren().add(createPause(900, () -> {
                sortedBars.get(currentI).markSorted();
                if (finalMinIdx != currentI) {
                    sortedBars.get(finalMinIdx).removeHighlight();
                }
            }));
        }
        mainSequence.getChildren().add(createPause(900, () -> {
            sortedBars.get(sortedBars.size() - 1).markSorted();
        }));

        mainSequence.play();
    }
    private SequentialTransition createSwapAnimation(int index1, int index2) {
        Bar bar1 = bars.get(index1);
        Bar bar2 = bars.get(index2);

        double distance = (index2 - index1) * (bar1.getBoundsInParent().getWidth() + spacing);
        TranslateTransition move1 = new TranslateTransition(Duration.millis(900), bar1);
        move1.setByX(distance);
        TranslateTransition move2 = new TranslateTransition(Duration.millis(900), bar2);
        move2.setByX(-distance);

        SequentialTransition swapSeq = new SequentialTransition();
        PauseTransition startPause = new PauseTransition(Duration.millis(900));
        startPause.setOnFinished(e -> {
            move1.play();
            move2.play();
        });
        swapSeq.getChildren().add(startPause);
        swapSeq.getChildren().add(new PauseTransition(Duration.millis(900)));

        return swapSeq;
    }
    private PauseTransition createPause(double millis, Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.millis(millis));
        if (action!=null){
            pause.setOnFinished(e -> action.run());
        }
        return pause;
    }
    public void resetAnimation(int[] array) {
        bars.clear();
        createBars(array);
    }
}