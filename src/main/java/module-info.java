module app.nmm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.javadoc;

    opens app.nmm to javafx.fxml;
    exports app.nmm;
    exports app.nmm.Controller;
    opens app.nmm.Controller to javafx.fxml;
}