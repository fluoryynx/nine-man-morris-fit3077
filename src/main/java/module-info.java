module app.nmm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens app.nmm to javafx.fxml;
    exports app.nmm;
    exports app.nmm.Cotroller;
    opens app.nmm.Cotroller to javafx.fxml;
}