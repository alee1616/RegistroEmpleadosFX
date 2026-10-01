module com.example.registroempleadosfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.registroempleadosfx to javafx.fxml;
    opens com.example.registroempleadosfx.controller to javafx.fxml;
    opens com.example.registroempleadosfx.model to javafx.base;

    exports com.example.registroempleadosfx;
}