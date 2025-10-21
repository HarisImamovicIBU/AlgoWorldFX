module org.bubblesort.bubblesort {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.bubblesort.bubblesort to javafx.fxml;
    exports org.bubblesort.bubblesort;
}