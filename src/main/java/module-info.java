module app.nmm {
    requires javafx.controls;
    requires javafx.fxml;

    requires jdk.javadoc;
    requires javatuples;

    opens app.nmm to javafx.fxml;
    exports app.nmm;
    exports app.nmm.Controller;
    opens app.nmm.Controller to javafx.fxml;
}