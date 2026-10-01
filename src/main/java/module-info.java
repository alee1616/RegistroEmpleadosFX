module com.example.registroempleadosfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.registroempleadosfx to javafx.fxml;
    exports com.example.registroempleadosfx;
}