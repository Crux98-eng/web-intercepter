module com.example.web_tester {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.web_tester to javafx.fxml;
    exports com.example.web_tester;
}