module com.speedtech_automotive {
    requires javafx.controls;

    requires javafx.fxml;
        requires javafx.web;
        requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.speedtech_automotive to javafx.fxml;
    exports com.speedtech_automotive;
    exports com.speedtech_automotive.controller;
    exports com.speedtech_automotive.utils;
    opens com.speedtech_automotive.utils to javafx.fxml;
}