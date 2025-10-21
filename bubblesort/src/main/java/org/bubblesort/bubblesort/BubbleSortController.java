package org.bubblesort.bubblesort;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class BubbleSortController {
    private final Pane pane;
    private final List<Bar> bars;
    private final double barWidth;
    private final double spacing = 10;
    public BubbleSortController(Pane pane, int[] array) {
        this.pane = pane;
        this.bars = new ArrayList<>();
        double paneWidth = 800;
        double availableWidth = paneWidth - (spacing * (array.length + 1));
        this.barWidth = availableWidth / array.length;

        int maxValue = 0;
        for(int val : array) {
            if(val>maxValue) {
                maxValue = val;
            }
        }
        for(int i=0;i<array.length;i++) {
            Bar bar = new Bar(array[i], barWidth, maxValue);
            bars.add(bar);
            positionBar(bar, i);
            pane.getChildren().add(bar);
        }
    }
    public Pane getPane() {
        return pane;
    }
    private void positionBar(Bar bar, int index) {
        double x = spacing + index * (barWidth + spacing);
        bar.setLayoutX(x);
        bar.setLayoutY(50);
    }
    public void sort() {
        SequentialTransition mainSequence = new SequentialTransition();
        for(int i=0;i<bars.size()-1;i++) {
            for(int j=0;j<bars.size()-i-1;j++) {
                Bar bar1 = bars.get(j);
                Bar bar2 = bars.get(j + 1);
                mainSequence.getChildren().add(createPause(900, () -> {
                    bar1.highlight();
                    bar2.highlight();
                }));
                if(bar1.getValue()>bar2.getValue()) {
                    mainSequence.getChildren().add(createSwapAnimation(j, j + 1));
                    bars.set(j, bar2);
                    bars.set(j + 1, bar1);
                }
                else {
                    mainSequence.getChildren().add(createPause(900, null));
                }
                mainSequence.getChildren().add(createPause(900, () -> {
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
        double distance = barWidth + spacing;
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
    //Implement the millis to be user defined
    private PauseTransition createPause(double millis, Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.millis(millis));
        if(action!=null) {
            pause.setOnFinished(e -> action.run());
        }
        return pause;
    }
    public void resetAnimation(int [] array){
        this.pane.getChildren().removeIf(node->node instanceof Bar);
        this.bars.clear();
        int maxValue = 0;
        for(int val : array) {
            if(val>maxValue) {
                maxValue = val;
            }
        }
        for(int i=0;i<array.length;i++) {
            Bar bar = new Bar(array[i], barWidth, maxValue);
            bars.add(bar);
            double x = spacing + i * (barWidth + spacing);
            bar.setLayoutX(x);
            bar.setLayoutY(50);
            pane.getChildren().add(bar);
        }
    }
}