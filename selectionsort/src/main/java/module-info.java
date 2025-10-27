module org.selectionsort.selectionsort {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.selectionsort.selectionsort to javafx.fxml;
    exports org.selectionsort.selectionsort;
}