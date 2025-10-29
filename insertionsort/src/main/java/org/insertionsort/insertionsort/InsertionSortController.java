package org.insertionsort.insertionsort;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class InsertionSortController {
    private final HBox container;
    private final List<Bar> bars;
    private final double spacing = 10;
    private double speedMultiplier = 1.0;
    private final double baseAnimationSpeed = 900;

    public InsertionSortController(HBox container, int[] array){
        this.container = container;
        this.bars = new ArrayList<>();
        container.setSpacing(spacing);
        createBars(array);
    }
    public void setSpeedMultiplier(double multiplier){
        this.speedMultiplier = multiplier;
    }
    public double getSpeedMultiplier(){
        return speedMultiplier;
    }
    private double getAnimationDuration(){
        return baseAnimationSpeed / speedMultiplier;
    }
    private void createBars(int[] array){
        container.getChildren().clear();
        int maxValue = 0;
        for (int val : array){
            if (val>maxValue){
                maxValue = val;
            }
        }
        double barWidth = 50;
        for (int val : array){
            Bar bar = new Bar(val, barWidth, maxValue);
            bars.add(bar);
            container.getChildren().add(bar);
        }
    }
    public void sort(){
        SequentialTransition mainSequence = new SequentialTransition();
        List<Bar> sortedBars = new ArrayList<>(bars);

        final Bar firstBar = sortedBars.get(0);
        mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
            firstBar.markSorted();
        }));

        for (int i = 1; i < sortedBars.size(); i++){
            final Bar keyBar = sortedBars.get(i);
            final int keyValue = keyBar.getValue();

            mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
                keyBar.highlightKey();
            }));

            int j = i - 1;
            while (j >= 0 && sortedBars.get(j).getValue() > keyValue){
                final Bar compareBar = sortedBars.get(j);

                mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
                    compareBar.highlight();
                }));

                int visualIndex1 = bars.indexOf(sortedBars.get(j));
                int visualIndex2 = bars.indexOf(sortedBars.get(j + 1));
                mainSequence.getChildren().add(createSwapAnimation(visualIndex1, visualIndex2));

                Bar temp = sortedBars.get(j + 1);
                sortedBars.set(j + 1, sortedBars.get(j));
                sortedBars.set(j, temp);

                bars.set(visualIndex1, sortedBars.get(j));
                bars.set(visualIndex2, sortedBars.get(j + 1));

                mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
                    compareBar.markSorted();
                }));

                j--;
            }
            mainSequence.getChildren().add(createPause(getAnimationDuration(), () -> {
                keyBar.markSorted();
            }));
        }
        mainSequence.play();
    }
    private SequentialTransition createSwapAnimation(int index1, int index2){
        Bar bar1 = bars.get(index1);
        Bar bar2 = bars.get(index2);

        double distance = (index2 - index1) * (bar1.getBoundsInParent().getWidth() + spacing);
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
    private PauseTransition createPause(double millis, Runnable action){
        PauseTransition pause = new PauseTransition(Duration.millis(millis));
        if (action!=null){
            pause.setOnFinished(e -> action.run());
        }
        return pause;
    }
    public void resetAnimation(int[] array){
        bars.clear();
        createBars(array);
    }
}