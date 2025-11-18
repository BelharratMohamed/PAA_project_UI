module UI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports UI.test;
    opens UI.test to javafx.fxml;

}