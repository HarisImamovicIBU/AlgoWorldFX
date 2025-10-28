module org.insertionsort.insertionsort {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.insertionsort.insertionsort to javafx.fxml;
    exports org.insertionsort.insertionsort;
}