module org.bubblesort.bubblesort {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.bubblesort.bubblesort to javafx.fxml;
    exports org.bubblesort.bubblesort;
}