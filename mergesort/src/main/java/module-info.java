module org.mergesort.mergesort {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.mergesort.mergesort to javafx.fxml;
    exports org.mergesort.mergesort;
}